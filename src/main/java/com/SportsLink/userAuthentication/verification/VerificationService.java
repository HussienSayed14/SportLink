package com.SportsLink.userAuthentication.verification;


import com.SportsLink.smsLimit.LimitTypeEnum;
import com.SportsLink.smsLimit.SmsDailyLimitService;
import com.SportsLink.userAuthentication.UserModel;
import com.SportsLink.userAuthentication.UserRepository;
import com.SportsLink.userAuthentication.verification.requests.VerifyUserRequest;
import com.SportsLink.userAuthentication.verification.responses.ResendCodeResponse;
import com.SportsLink.utils.DateTimeService;
import com.SportsLink.utils.GenericResponse;
import com.SportsLink.utils.MessageService;
import com.SportsLink.utils.SmsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;


@Service
@RequiredArgsConstructor
public class VerificationService {
    private final VerificationRepository verificationRepository;
    private final DateTimeService dateTimeService;
    private final SmsService smsService;
    private final UserRepository userRepository;
    private final MessageService messageService;
    private final SmsDailyLimitService smsDailyLimitService;
    private static final SecureRandom random = new SecureRandom();
    private static final Logger logger = LoggerFactory.getLogger(VerificationService.class);

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz"
            + "0123456789"
            + "*-_";


    public boolean createAndSendVerificationCode(UserModel user,String channel){

        try {
            String verificationCode = generateVerificationCode(6);
            System.out.println("Verification Code: " + verificationCode);

            VerificationModel userVerification = VerificationModel.builder()
                    .verification_code(verificationCode)
                    .user_id(user)
                    .verification_channel(channel)
                    .attempt_count(0)
                    .created_at(dateTimeService.getCurrentTimestamp())
                    .resend_at(dateTimeService.addMinutesToNow(3))
                    .expires_at(dateTimeService.addMinutesToNow(10))
                    .build();
            verificationRepository.save(userVerification);

            smsDailyLimitService.incrementSmsDailyLimit(user, LimitTypeEnum.USER_VERIFICATION.toString());
            smsService.sendWhatsAppVerificationMessage(user.getPhone_number(),
                    verificationCode,
                    messageService.getMessage("twilio.verificationMessage.template.sid"));
            return true;

        }catch (Exception e){
            logger.error("An Error happened while creating verification code for: "+ user.getPhone_number() + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
            return false;

        }

    }

    private static String generateVerificationCode(int length) {
        StringBuilder verificationCode = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // Generate a random index to pick a character from CHARACTERS
            int index = random.nextInt(CHARACTERS.length());
            // Append the character to the verification code
            verificationCode.append(CHARACTERS.charAt(index));
        }
        return verificationCode.toString();
    }

    public ResponseEntity<GenericResponse> verifyUser(VerifyUserRequest request) {
        GenericResponse response = new GenericResponse();
        try {
            VerificationModel verificationRecord =
                    verificationRepository.getVerificationRecordByUserId(request.getUserId());

            if(verificationRecord == null){
                response.setForbiddenRequest(messageService.getMessage("user.doesNotExist"));
                return ResponseEntity.status(response.getHttpStatus()).body(response);
            }

            if(isCodeCorrect(verificationRecord, request.getCode(), response)
                    && isCodeValid(verificationRecord,response) ){
                userRepository.verifyUser(request.getUserId());
                response.setSuccessful(messageService.getMessage("generic.success"));
            }

        }catch (Exception e){
            response.setServerError(messageService.getMessage("unexpected.error"));
            logger.error("An Error happened while verifying user: "+ request.getUserId() + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();

        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    private boolean isCodeValid(VerificationModel verificationRecord,GenericResponse response){
        if(dateTimeService.isRequestNonExpired(verificationRecord.getCreated_at(),verificationRecord.getExpires_at())){
            return true;
        }
        response.setUnauthorizedRequest(messageService.getMessage("verificationCode.expired"));
        resendVerificationCode(verificationRecord.getUser_id().getUser_id());
        return false;
    }

    private boolean isCodeCorrect(VerificationModel verificationRecord,String enteredCode,GenericResponse response){
        if(verificationRecord.getVerification_code().equals(enteredCode)){
            return true;
        }
        verificationRepository.incrementAttemptById(verificationRecord.getVerification_id());
        response.setUnauthorizedRequest(messageService.getMessage("verificationCode.wrong"));
        return false;
    }

    public ResponseEntity<GenericResponse> resendVerificationCode(int userId){
        ResendCodeResponse response = new ResendCodeResponse();
        try{
            VerificationModel verificationRecord =
                    verificationRepository.getVerificationRecordByUserId(userId);

            if(verificationRecord == null){
                response.setForbiddenRequest(messageService.getMessage("user.doesNotExist"));
                return ResponseEntity.status(response.getHttpStatus()).body(response);
            }

            if(!smsDailyLimitService.canSendSms(verificationRecord.getUser_id(),"USER_VERIFICATION",10)){
                response.setForbiddenRequest(messageService.getMessage("sms.daily.limit"));
                return ResponseEntity.status(response.getHttpStatus()).body(response);
            }

            if(canGetNewCode(verificationRecord.getResend_at(),response)){

                String userPhone = verificationRecord.getUser_id().getPhone_number();
                String verificationCode = generateVerificationCode(6);
                System.out.println("Verification Code: " + verificationCode);

                verificationRecord.setVerification_code(verificationCode);
                verificationRecord.setCreated_at(dateTimeService.getCurrentTimestamp());
                resetResendTime(verificationRecord);
                verificationRepository.save(verificationRecord);

                smsService.sendWhatsAppVerificationMessage(userPhone, verificationCode,"HXa9544fd879f225ee816cb885c7e20672");
                smsDailyLimitService.incrementSmsDailyLimit(verificationRecord.getUser_id(),
                        LimitTypeEnum.USER_VERIFICATION.toString());
                response.setSuccessful(messageService.getMessage("generic.success"));
            }

        }catch (Exception e){
            response.setServerError(messageService.getMessage("unexpected.error"));
            logger.error("An Error happened while resending code to user: "+ userId + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    private boolean canGetNewCode(Timestamp resendAt,ResendCodeResponse response){
        Timestamp now = new Timestamp(System.currentTimeMillis());

        if (now.after(resendAt) || now.equals(resendAt)) {
            return true;
        } else {
            // User cannot resend yet; calculate time remaining
            long timeRemaining = resendAt.getTime() - now.getTime(); // in milliseconds
            response.setForbiddenRequest(messageService.getMessage("verificationCode.wait"));
            response.setTimeRemaining(timeRemaining);
            return false;
        }
    }

    private void resetResendTime(VerificationModel verificationRecord){
        if(verificationRecord.getAttempt_count() <= 6){
            verificationRecord.setResend_at(dateTimeService.addMinutesToNow(3));
        }else if(verificationRecord.getAttempt_count() > 6 && verificationRecord.getAttempt_count() <= 10){
            verificationRecord.setResend_at(dateTimeService.addMinutesToNow(5));
        }else if (verificationRecord.getAttempt_count() > 10 && verificationRecord.getAttempt_count() <= 15){
            verificationRecord.setResend_at(dateTimeService.addMinutesToNow(20));
        }else{
            verificationRecord.setResend_at(dateTimeService.addMinutesToNow(60));
        }

    }





}
