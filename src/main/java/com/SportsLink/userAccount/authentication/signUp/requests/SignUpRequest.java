package com.SportsLink.userAccount.authentication.signUp.requests;

import com.SportsLink.userAccount.authentication.RolesEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    @NotNull(message = "Phone number cannot be null")
    @Size(min = 8, max = 15, message = "Phone number must be between 8 and 15 characters")
    @Pattern(regexp = "\\+?[0-9]+", message = "Phone number must contain only digits and may start with '+'")
    private String phoneNumber;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password must contain at least one letter and one number, and be at least 8 characters long"
    )
    private String password;

    @NotNull(message = "Full name cannot be null")
    @Size(max = 100, message = "Full name must be less than or equal to 100 characters")
    private String fullName;

    @Size(max = 120, message = "Email must be less than or equal to 254 characters")
    private String email;

    @NotNull(message = "Role cannot be null")
    private RolesEnum role;
}
