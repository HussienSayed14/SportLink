package com.SportsLink.userAuthentication.verification;


import com.SportsLink.userAuthentication.UserModel;
import com.SportsLink.userAuthentication.UserRepository;
import com.SportsLink.userAuthentication.verification.requests.VerifyUserRequest;
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

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final VerificationRepository verificationRepository;
    private final DateTimeService dateTimeService;
    private final SmsService smsService;
    private final UserRepository userRepository;
    private final MessageService messageService;
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
                    .is_verified(false)
                    .attempt_count(0)
                    .created_at(dateTimeService.getCurrentTimestamp())
                    .last_created(dateTimeService.getCurrentTimestamp())
                    .expires_at(dateTimeService.addMinutesToNow(10))
                    .build();
            verificationRepository.save(userVerification);

            StringBuilder message = new StringBuilder();
            message.append(messageService.getMessage("user.verificationMessage"))
                    .append("\n")
                    .append(verificationCode);

            smsService.sendSmsMessage(user.getPhone_number(), String.valueOf(message));
            return  true;

        }catch (Exception e){
            logger.error("An Error happened while creating verification code for: "+ user.getPhone_number() + "\n" +
                    "Error Message: " + e.getMessage());
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





        }catch (Exception e){
            response.setServerError(messageService.getMessage("unexpected.error"));
            logger.error("An Error happened while verifying user: "+ request.getUserId() + "\n" +
                    "Error Message: " + e.getMessage());

        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    private boolean isCodeValid(VerificationModel verificationRecord,GenericResponse response){
        if(dateTimeService.isRequestNonExpired(verificationRecord.getCreated_at(),verificationRecord.getExpires_at())){
            return true;
        }
        response.setUnauthorizedRequest(messageService.getMessage("verificationCode.expired"));
        //Call Resend Message
        return false;
    }

    private boolean isCodeCorrect(VerificationModel verificationRecord,String enteredCode,GenericResponse response){
        if(verificationRecord.getVerification_code().equals(enteredCode)){
            return true;
        }
        response.setUnauthorizedRequest(messageService.getMessage("verificationCode.wrong"));
        return false;
    }





}
