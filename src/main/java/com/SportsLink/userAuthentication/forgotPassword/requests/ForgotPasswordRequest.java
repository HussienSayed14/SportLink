package com.SportsLink.userAuthentication.forgotPassword.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordRequest {
    @NotNull(message = "{phoneNumber.notNull}")
    @Size(min = 10, max = 11, message = "{phoneNumber.size}")
    @Pattern(regexp =  "^[01][0-9]*$", message = "{phoneNumber.invalid}")
    private String phone;

    @NotNull(message = "{countryCode.notNull}")
    private String countryCode;

}
