package com.SportsLink.userAuthentication.forgotPassword;

import com.SportsLink.smsLimit.LimitTypeEnum;
import com.SportsLink.smsLimit.SmsDailyLimitService;
import com.SportsLink.userAuthentication.UserModel;
import com.SportsLink.userAuthentication.UserRepository;
import com.SportsLink.userAuthentication.forgotPassword.requests.ForgotPasswordRequest;
import com.SportsLink.userAuthentication.forgotPassword.requests.NewPasswordRequest;
import com.SportsLink.utils.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final MessageService messageService;
    private final UserRepository userRepository;
    private final SmsDailyLimitService smsDailyLimitService;
    private final DateTimeService dateTimeService;
    private final SmsService smsService;
    private final Environment environment;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordService.class);

    public ResponseEntity<GenericResponse> createForgotPassword(ForgotPasswordRequest request) {
        GenericResponse response = new GenericResponse();
        try{
            UserModel user =
                    userRepository.findUserByPhoneNumber(PhoneNumberService.formatPhoneNumber(request.getCountryCode(), request.getPhone()));

            if(user == null){
                response.setBadRequest(messageService.getMessage("user.non.exist"));
                ResponseEntity.status(response.getHttpStatus()).body(response);
            }

            if(!smsDailyLimitService.canSendSms(user,"FORGOT_PASSWORD",6)){
                response.setForbiddenRequest(messageService.getMessage("sms.daily.limit"));
                return ResponseEntity.status(response.getHttpStatus()).body(response);
            }

            String forgotPasswordToken = UUID.randomUUID().toString();

            ForgotPasswordModel forgotPasswordRecord = forgotPasswordRepository.getUserForgotPasswordByUserId(user);

            if(forgotPasswordRecord == null){
                forgotPasswordRecord = ForgotPasswordModel.builder()
                        .created_at(dateTimeService.getCurrentTimestamp())
                        .is_used(false)
                        .user_id(user)
                        .UUID(forgotPasswordToken)
                        .build();
            }

            forgotPasswordRecord.setUUID(forgotPasswordToken);
            forgotPasswordRepository.save(forgotPasswordRecord);
            sendForgotPassword(forgotPasswordToken,user);
            response.setSuccessful(messageService.getMessage("forgot.password.success"));

        }catch (Exception e){
            logger.error("An Error happened while creating forgot password code for: "+ request.getPhone() + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
            response.setServerError(messageService.getMessage("unexpected.error"));
        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }



    private void sendForgotPassword(String token, UserModel user){
        String redirectUrl = environment.getProperty("frontend.forgot.password.url") 
                + "?token=" + token + "&id=" + user.getUser_id();
        smsDailyLimitService.incrementSmsDailyLimit(user, LimitTypeEnum.FORGOT_PASSWORD.toString());
        smsService.sendWhatsAppForgotPasswordMessage(user.getPhone_number(),
                redirectUrl,
                messageService.getMessage("twilio.forgot.password.template.id"));
    }



    public ResponseEntity<GenericResponse> resetUserPassword(NewPasswordRequest request) {
        GenericResponse response = new GenericResponse();
        try{
            ForgotPasswordModel record = forgotPasswordRepository.getUserForgotPasswordByUserIdInt(request.getUserId());
            if(record == null){
                response.setBadRequest(messageService.getMessage("user.non.exist"));
                ResponseEntity.status(response.getHttpStatus()).body(response);
            }

            if(isRequestValid(record,response)
                    && isTokenValid(record,request,response)){
                record.getUser_id().setPassword_hash(bCryptPasswordEncoder.encode(request.getPassword()));
                userRepository.save(record.getUser_id());
                response.setSuccessful(messageService.getMessage("forgot.password.changed"));
            }

        }catch (Exception e){
            logger.error("An Error happened while resetting user password: "+ request.getUserId() + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
            response.setServerError(messageService.getMessage("unexpected.error"));
        }

        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }


    private boolean isRequestValid(ForgotPasswordModel forgotPasswordModel,GenericResponse response){
        if(dateTimeService.isRequestNonExpired(forgotPasswordModel.getCreated_at(),dateTimeService.addMinutesToNow(20))){
            return true;
        }
        response.setForbiddenRequest(messageService.getMessage("forgot.password.expired"));
        return false;

    }


    private boolean isTokenValid(ForgotPasswordModel forgotPasswordModel,
                                 NewPasswordRequest request,
                                 GenericResponse response){
        if(!forgotPasswordModel.getUUID().equals(request.getToken())
                || forgotPasswordModel.getUser_id().getUser_id() != request.getUserId()){

            response.setForbiddenRequest(messageService.getMessage("forgot.password.invalid"));
            return false;

        }
        return true;
    }
}
