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
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sportsLink/api/v1/userAuth")
@RequiredArgsConstructor
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



}
