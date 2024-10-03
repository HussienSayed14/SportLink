package com.SportsLink.userAccount.authentication.signUp;


import com.SportsLink.userAccount.authentication.UserRepository;
import com.SportsLink.userAccount.authentication.signUp.requests.SignUpRequest;
import com.SportsLink.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;


    public ResponseEntity<GenericResponse> signUp(SignUpRequest request) {
        GenericResponse response = new GenericResponse();
        try {

        }catch (Exception e){

        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
