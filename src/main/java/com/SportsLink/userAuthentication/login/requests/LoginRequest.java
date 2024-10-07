package com.SportsLink.userAuthentication.login.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotNull(message = "{phoneNumber.notNull}")
    @Size(min = 10, max = 11, message = "{phoneNumber.size}")
    @Pattern(regexp =  "^[01][0-9]*$", message = "{phoneNumber.invalid}")
    private String phone;

    @NotNull(message = "{password.notNull}")
    @Size(min = 8, message = "{password.size}")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$",
            message ="{password.size}"
    )
    private String password;

    @NotNull(message = "{countryCode.notNull}")
    private String countryCode;
}
