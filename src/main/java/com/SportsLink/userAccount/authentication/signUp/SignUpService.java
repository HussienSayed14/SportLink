package com.SportsLink.userAccount.authentication.signUp;


import com.SportsLink.userAccount.authentication.UserModel;
import com.SportsLink.userAccount.authentication.UserRepository;
import com.SportsLink.userAccount.authentication.signUp.requests.SignUpRequest;
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
    private static final Logger logger = LoggerFactory.getLogger(SignUpService.class);


    public ResponseEntity<GenericResponse> signUp(SignUpRequest request) {
        GenericResponse response = new GenericResponse();
        try {

            UserModel user = userRepository.findUserByPhoneNumber(request.getPhoneNumber());
            if(user != null){
                response.setBadRequest(messageService.getMessage("register.fail.phoneAlreadyExist"));
                return ResponseEntity.status(response.getHttpStatus()).body(response);
            }

             user = UserModel.builder()
                    .phone_number(request.getPhoneNumber())
                    .email(request.getEmail())
                    .name(request.getFullName())
                    .role(request.getRole())
                    .blocked_until(null)
                    .created_at(dateTimeService.getCurrentTimestamp())
                    .password_hash(passwordEncoder.encode(request.getPassword()))
                    .build();
            userRepository.save(user);
            response.setSuccessful(messageService.getMessage("register.success"));
        }catch (Exception e){
            response.setServerErrorError(messageService.getMessage("unexpected.error"));
            e.printStackTrace();
            logger.error("An Error happened while creating user: "+ request.getPhoneNumber() + "\n" +
                    "Error Message: " + e.getMessage());
            logger.error(e.getMessage());
        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
