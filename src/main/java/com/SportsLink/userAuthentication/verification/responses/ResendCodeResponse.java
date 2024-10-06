package com.SportsLink.userAuthentication.verification.responses;

import com.SportsLink.utils.GenericResponse;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class ResendCodeResponse extends GenericResponse {
    private long timeRemaining;
}
