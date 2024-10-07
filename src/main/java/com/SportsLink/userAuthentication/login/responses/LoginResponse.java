package com.SportsLink.userAuthentication.login.responses;

import com.SportsLink.utils.GenericResponse;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class LoginResponse extends GenericResponse {
    private String token;
    private String role;
    private String fullName;
    private boolean isVerified;
}
