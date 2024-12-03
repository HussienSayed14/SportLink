package com.SportsLink.userAuthentication.signUp;


import com.SportsLink.address.CityModel;
import com.SportsLink.address.DistrictModel;
import com.SportsLink.address.GovernoratesModel;
import com.SportsLink.userAuthentication.RolesEnum;
import com.SportsLink.userAuthentication.UserModel;
import com.SportsLink.userAuthentication.UserRepository;
import com.SportsLink.userAuthentication.signUp.requests.SignUpRequest;
import com.SportsLink.userAuthentication.signUp.responses.SignUpResponse;
import com.SportsLink.userAuthentication.verification.VerificationService;
import com.SportsLink.utils.DateTimeService;
import com.SportsLink.utils.GenericResponse;
import com.SportsLink.utils.MessageService;
import com.SportsLink.utils.PhoneNumberService;
import jakarta.persistence.EntityManager;
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
    private final EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(SignUpService.class);


    public ResponseEntity<GenericResponse> signUp(SignUpRequest request) {
        SignUpResponse response = new SignUpResponse();
        try {

            UserModel user = userRepository.findVerifiedUserByPhoneNumber(request.getPhoneNumber());
            if(user != null){
                response.setBadRequest(messageService.getMessage("register.fail.phoneAlreadyExist"));
                return ResponseEntity.status(response.getHttpStatus()).body(response);
            }

             user = UserModel.builder()
                    .phone_number(PhoneNumberService.formatPhoneNumber(request.getCountryCode(),request.getPhoneNumber()))
                    .email(request.getEmail())
                    .name(request.getFullName())
                    .role(RolesEnum.ROLE_USER)
                    .blocked_until(null)
                     .is_activated(true)
                    .created_at(dateTimeService.getCurrentTimestamp())
                    .password_hash(passwordEncoder.encode(request.getPassword()))
                    .build();

            if(request.getGovernorateId() != 0){
                GovernoratesModel gov = entityManager.getReference(GovernoratesModel.class, request.getGovernorateId());
                user.setGovernorate(gov);
            }
            if(request.getCityId() != 0){
                CityModel city = entityManager.getReference(CityModel.class, request.getCityId());
                user.setCity(city);
            }
            if(request.getDistrictId() != 0){
                DistrictModel district = entityManager.getReference(DistrictModel.class, request.getDistrictId());
                user.setDistrict(district);
            }

            UserModel createdUser = userRepository.save(user);

            verificationService.createAndSendVerificationCode(createdUser,"SMS");
            response.setUserId(createdUser.getUser_id());
            response.setPhoneNumber(createdUser.getPhone_number());
            response.setSuccessful(messageService.getMessage("register.success"));
        }catch (Exception e){
            response.setServerError(messageService.getMessage("unexpected.error"));
            logger.error("An Error happened while creating user: "+ request.getPhoneNumber() + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();

        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

}
