package com.SportsLink.userAccount.authentication;


import com.SportsLink.userAccount.authentication.signUp.SignUpService;
import com.SportsLink.userAccount.authentication.signUp.requests.SignUpRequest;
import com.SportsLink.utils.GenericResponse;
import com.SportsLink.utils.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final MessageService messageService;

    @PostMapping(value = "/signUp", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GenericResponse> signUp(@Valid @RequestBody SignUpRequest request, BindingResult bindingResult){
         if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new GenericResponse(errorMessage));
        }
         return signUpService.signUp(request);
    }



}
