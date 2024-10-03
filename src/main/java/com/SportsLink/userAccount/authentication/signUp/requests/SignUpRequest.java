package com.SportsLink.userAccount.authentication.signUp.requests;

import com.SportsLink.userAccount.authentication.RolesEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    @NotNull(message = "{phoneNumber.notNull}")
    @Size(min = 8, max = 15, message = "{phoneNumber.size}")
    @Pattern(regexp = "\\+?[0-9]+", message = "{phoneNumber.invalid}")
    private String phoneNumber;

    @NotNull(message = "{password.notNull}")
    @Size(min = 8, message = "{password.size}")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$",
            message ="{password.size}"
    )
    private String password;

    @NotNull(message = "{fullName.notNull}")
    @Size(max = 100, message = "{fullName.size}")
    private String fullName;

    @Size(max = 120, message = "{email.size}")
    private String email;

    @NotNull(message = "{role.notNull}")
    private RolesEnum role;
}

