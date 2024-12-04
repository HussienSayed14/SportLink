package com.SportsLink.userData;


import com.SportsLink.utils.GenericResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        System.out.println("Calling logout");
        // Create a new Cookie with the same name as the JWT cookie
        Cookie cookie = new Cookie("token", null); // Set value to null
        // Set the cookie attributes to ensure it gets removed in the browser
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Same as when you set it (ensure it's only sent over HTTPS)
        cookie.setPath("/"); // Same as the original cookie path
        cookie.setMaxAge(0); // This will remove the cookie immediately

        // Add the cookie to the response to remove it from the browser
        response.addCookie(cookie);

        // Return success message
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }
}
