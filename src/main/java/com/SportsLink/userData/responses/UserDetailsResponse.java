package com.SportsLink.userData.responses;


import com.SportsLink.userData.dtos.UserDetailsDto;
import com.SportsLink.utils.GenericResponse;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserDetailsResponse extends GenericResponse {
    UserDetailsDto userData;
}
