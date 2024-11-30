package com.SportsLink.userAuthentication;


import com.SportsLink.userAuthentication.forgotPassword.ForgotPasswordService;
import com.SportsLink.userAuthentication.forgotPassword.requests.ForgotPasswordRequest;
import com.SportsLink.userAuthentication.forgotPassword.requests.NewPasswordRequest;
import com.SportsLink.userAuthentication.login.LoginService;
import com.SportsLink.userAuthentication.login.requests.LoginRequest;
import com.SportsLink.userAuthentication.signUp.SignUpService;
import com.SportsLink.userAuthentication.signUp.requests.SignUpRequest;
import com.SportsLink.userAuthentication.verification.VerificationService;
import com.SportsLink.userAuthentication.verification.requests.VerifyUserRequest;
import com.SportsLink.utils.GenericResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sportsLink/api/v1/userAuth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "User Authentication" , description = "Apis That is Responsible User Authenticated Operations, does not need JWT token")
public class AuthenticationController {

    private final SignUpService signUpService;
    private final LoginService loginService;
    private final VerificationService verificationService;
    private final ForgotPasswordService forgotPasswordService;


    @PostMapping(value = "/signUp", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GenericResponse> signUp(@Valid @RequestBody SignUpRequest request, BindingResult bindingResult){
         if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new GenericResponse(errorMessage));
        }
         return signUpService.signUp(request);
    }

    @PostMapping(value = "/verifyUser")
    ResponseEntity<GenericResponse> verifyUser(@Valid @RequestBody VerifyUserRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new GenericResponse(errorMessage));
        }
        return verificationService.verifyUser(request);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GenericResponse> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new GenericResponse(errorMessage));
        }
        return loginService.login(request,response);
    }

    @PostMapping("/resendCode/{userId}")
    ResponseEntity<GenericResponse> resendVerificationCode(@PathVariable int userId){
        return verificationService.resendVerificationCode(userId);
    }

    @PostMapping("/forgotPassword")
    ResponseEntity<GenericResponse> createForgotPassword(@Valid @RequestBody ForgotPasswordRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new GenericResponse(errorMessage));
        }
        return forgotPasswordService.createForgotPassword(request);
    }

    @PostMapping("/resetPassword")
    ResponseEntity<GenericResponse> resetUserPassword(@Valid @RequestBody NewPasswordRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new GenericResponse(errorMessage));
        }
        return forgotPasswordService.resetUserPassword(request);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // Create a new Cookie with the same name as the JWT cookie
        Cookie cookie = new Cookie("token", null); // Set value to null
        // Set the cookie attributes to ensure it gets removed in the browser
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Same as when you set it (ensure it's only sent over HTTPS)
        cookie.setPath("/"); // Same as the original cookie path
        cookie.setMaxAge(0); // This will remove the cookie immediately

        // Add the cookie to the response to remove it from the browser
        response.addCookie(cookie);

        // Return success message
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }


}
