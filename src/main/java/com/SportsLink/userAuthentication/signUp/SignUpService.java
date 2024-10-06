package com.SportsLink.userAuthentication.signUp;


import com.SportsLink.userAuthentication.UserModel;
import com.SportsLink.userAuthentication.UserRepository;
import com.SportsLink.userAuthentication.signUp.requests.SignUpRequest;
import com.SportsLink.userAuthentication.signUp.responses.SignUpResponse;
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
public class SignUpService {

    private final UserRepository userRepository;
    private final DateTimeService dateTimeService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MessageService messageService;
    private final VerificationService verificationService;
    private static final Logger logger = LoggerFactory.getLogger(SignUpService.class);


    public ResponseEntity<GenericResponse> signUp(SignUpRequest request) {
        SignUpResponse response = new SignUpResponse();
        try {

            UserModel user = userRepository.findUserByPhoneNumber(request.getPhoneNumber());
            if(user != null){
                response.setBadRequest(messageService.getMessage("register.fail.phoneAlreadyExist"));
                return ResponseEntity.status(response.getHttpStatus()).body(response);
            }

             user = UserModel.builder()
                    .phone_number(formatPhoneNumber(request.getCountryCode(),request.getPhoneNumber()))
                    .email(request.getEmail())
                    .name(request.getFullName())
                    .role(request.getRole())
                    .blocked_until(null)
                     .is_activated(true)
                    .created_at(dateTimeService.getCurrentTimestamp())
                    .password_hash(passwordEncoder.encode(request.getPassword()))
                    .build();

            UserModel createdUser = userRepository.save(user);

            verificationService.createAndSendVerificationCode(createdUser,"SMS");
            response.setUserId(createdUser.getUser_id());
            response.setSuccessful(messageService.getMessage("register.success"));
        }catch (Exception e){
            response.setServerError(messageService.getMessage("unexpected.error"));
            e.printStackTrace();
            logger.error("An Error happened while creating user: "+ request.getPhoneNumber() + "\n" +
                    "Error Message: " + e.getMessage());

        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
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
