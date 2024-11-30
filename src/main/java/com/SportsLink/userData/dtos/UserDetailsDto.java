package com.SportsLink.userData.dtos;

import com.SportsLink.userAuthentication.RolesEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
public class UserDetailsDto {
    private int userId;
    private String phoneNumber;
    private String name;
    private String role;
    private Timestamp createdAt;
}
