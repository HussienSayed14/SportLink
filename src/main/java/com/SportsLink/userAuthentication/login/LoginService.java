package com.SportsLink.userAuthentication.login;

import com.SportsLink.config.JwtService;
import com.SportsLink.loginAudit.LoginAuditService;
import com.SportsLink.userAuthentication.UserModel;
import com.SportsLink.userAuthentication.UserRepository;
import com.SportsLink.userAuthentication.login.requests.LoginRequest;
import com.SportsLink.userAuthentication.login.responses.LoginResponse;
import com.SportsLink.userAuthentication.verification.VerificationService;
import com.SportsLink.utils.DateTimeService;
import com.SportsLink.utils.GenericResponse;
import com.SportsLink.utils.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final DateTimeService dateTimeService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MessageService messageService;
    private final JwtService jwtService;
    private final VerificationService verificationService;
    private final LoginAuditService loginAuditService;
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    public ResponseEntity<GenericResponse> login(LoginRequest request) {
        LoginResponse response = new LoginResponse();

        try{
            UserModel user =
                    userRepository.findUserByPhoneNumber(
                            formatPhoneNumber(request.getCountryCode(), request.getPhone()));

            if(user == null){
                response.setBadRequest(messageService.getMessage("user.non.exist"));
                return ResponseEntity.status(response.getHttpStatus()).body(response);
            }

            if(isVerified(user,response)
                    && !isBlocked(user,response)
                    && isPasswordCorrect(request.getPassword(), user,response)){
                user.setLast_login(dateTimeService.getCurrentTimestamp());
                user.setFailed_attempts(0);

                response.setRole(String.valueOf(user.getRole()));
                response.setFullName(user.getName());
                response.setToken(jwtService.generateToken(user,user.getUser_id()));
                response.setSuccessful(messageService.getMessage("generic.success"));
                userRepository.save(user);
                loginAuditService.createSuccessLoginAudit(user,response.getToken(),"1.1.1",response.getMessage());

            }else {
                loginAuditService.createFailedLoginAudit(user,response.getMessage());
                userRepository.save(user);
            }


        }catch (Exception e){
            response.setServerError(messageService.getMessage("unexpected.error"));
            logger.error("An Error happened while creating user: "+ request.getPhone() + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }


    private boolean isPasswordCorrect(String plainPassword, UserModel user, LoginResponse response){
        if(passwordEncoder.matches(plainPassword,user.getPassword_hash())){
            user.setFailed_attempts(0);
            return true;
        }
        user.setFailed_attempts(user.getFailed_attempts() + 1);
        response.setUnauthorizedRequest(
                messageService.getMessage("login.wrong.password") + (6 - user.getFailed_attempts()));

        if(user.getFailed_attempts() > 6){
            // Block User
            user.set_blocked(true);
            user.setBlocked_until(dateTimeService.addHoursToNow(1));
            response.setUnauthorizedRequest(
                    messageService.getMessage("login.too.many.wrong.attempts") + user.getBlocked_until());

        }
        return false;
    }

    private boolean isVerified(UserModel user, LoginResponse response){
        if(user.is_verified()){
            response.setVerified(true);
            return true;
        }

        response.setForbiddenRequest(messageService.getMessage("user.not.verified"));
        response.setVerified(false);
        verificationService.resendVerificationCode(user.getUser_id());
        return false;
    }

    private boolean isBlocked(UserModel user, LoginResponse response){
        if(user.is_blocked()){
            if(dateTimeService.isRequestBlocked(user.getBlocked_until())){
                response.setUnauthorizedRequest(
                        messageService.getMessage("login.too.many.wrong.attempts") + user.getBlocked_until());
                return true;
            }
            user.set_blocked(false);
            user.setFailed_attempts(0);
        }
        return false;
    }



    private String formatPhoneNumber(String countryCode, String phoneNumber){
        countryCode = countryCode.trim();
        phoneNumber = phoneNumber.trim();
        if (phoneNumber.startsWith("0")) {
            phoneNumber = phoneNumber.substring(1);
        }
        return countryCode + phoneNumber;
    }
}
