package com.SportsLink.userData;


import com.SportsLink.config.JwtService;
import com.SportsLink.userAuthentication.UserRepository;
import com.SportsLink.userData.dtos.UserDetailsDto;
import com.SportsLink.userData.dtos.UserDetailsProjection;
import com.SportsLink.userData.responses.UserDetailsResponse;
import com.SportsLink.utils.GenericResponse;
import com.SportsLink.utils.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDataService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MessageService messageService;
    private static final Logger logger = LoggerFactory.getLogger(UserDataService.class);
    public ResponseEntity<GenericResponse> getUserDetails(HttpServletRequest httpServletRequest) {
        UserDetailsResponse response = new UserDetailsResponse();
        try{
            int userId = jwtService.extractUserIdFromCookie(httpServletRequest);
            UserDetailsProjection projection = userRepository.getUserDetailsById(userId);
            UserDetailsDto userDetails = new UserDetailsDto(
                    projection.getId(),
                    projection.getPhone(),
                    projection.getName(),
                    projection.getRole(),
                    projection.getTimestamp());
            response.setUserData(userDetails);
            response.setSuccessful(messageService.getMessage("generic.success"));
        }catch (Exception e){
            response.setServerError(messageService.getMessage("unexpected.error"));
            logger.error("An Error happened while getting user details " + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
