package com.SportsLink.userData.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
public class UserDetailsDto {
    private int id;
    private String phone;
    private String name;
    private String role;
    private Timestamp timestamp;
}
