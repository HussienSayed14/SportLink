package com.SportsLink.userAuthentication.login.responses;

import com.SportsLink.userData.dtos.UserDetailsDto;
import com.SportsLink.utils.GenericResponse;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginResponse extends GenericResponse {
    UserDetailsDto userDetails;
    private boolean isVerified;
}
