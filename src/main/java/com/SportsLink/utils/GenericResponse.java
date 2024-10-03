package com.SportsLink.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class GenericResponse {
    private String responseCode;
    private boolean success;
    @JsonIgnore
    private HttpStatus httpStatus;
    private String message;

    public GenericResponse(String message){
        setResponseCode("-2");
        setSuccess(false);
        setMessage(message);
        setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    public void setServerErrorError(String message){
        setResponseCode("-1");
        setSuccess(false);
        setMessage(message);
        setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public void setSuccessful(String message){
        setResponseCode("0");
        setSuccess(true);
        setMessage(message);
        setHttpStatus(HttpStatus.OK);
    }

    public void setBadRequest(String message){
        setResponseCode("-2");
        setSuccess(false);
        setMessage(message);
        setHttpStatus(HttpStatus.BAD_REQUEST);
    }
}
