package com.SportsLink.userAuthentication.forgotPassword.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPasswordRequest {

    @NotNull
    private String token;
    @NotNull
    private int userId;

    @NotNull(message = "{password.notNull}")
    @Size(min = 8, message = "{password.size}")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$",
            message ="{password.size}"
    )
    private String password;
}
