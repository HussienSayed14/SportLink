package com.SportsLink.userData.dtos;

import java.sql.Timestamp;

public interface UserDetailsProjection {

    int getId();
    String getName();
    String getRole();
    Timestamp getTimestamp();
    String getPhone();
}
