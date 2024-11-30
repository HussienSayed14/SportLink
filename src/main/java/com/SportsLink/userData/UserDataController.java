package com.SportsLink.userData;


import com.SportsLink.utils.GenericResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sportsLink/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@Tag(name = "User Data" , description = "Apis That is Responsible User Authenticated Operations, does not need JWT token")
public class UserDataController {
    private final UserDataService userDataService;
    @GetMapping("/getDetails")
    ResponseEntity<GenericResponse> getUserDetails(HttpServletRequest httpServletRequest){
        return userDataService.getUserDetails(httpServletRequest);
    }
}
