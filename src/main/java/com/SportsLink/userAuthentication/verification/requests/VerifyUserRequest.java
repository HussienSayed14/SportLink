package com.SportsLink.userAuthentication.verification.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyUserRequest {
    @NotNull
    private int userId;
    @NotNull
    private String code;

}
