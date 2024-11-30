package com.SportsLink.userAuthentication.signUp.responses;

import com.SportsLink.utils.GenericResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponse extends GenericResponse {
    private int userId;
    private String phoneNumber;
}
