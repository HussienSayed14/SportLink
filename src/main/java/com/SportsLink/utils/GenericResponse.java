package com.SportsLink.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class GenericResponse {
    @JsonIgnore
    @Autowired
    private MessageSource messageSource;
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

    public void setSystemError(){
        setResponseCode("-1");
        setSuccess(false);
        setMessage(messageSource.getMessage("unexpected.error", null, LocaleContextHolder.getLocale()));
        setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public void setGenericSuccessful(){
        setResponseCode("0");
        setSuccess(true);
        setMessage(messageSource.getMessage("generic.success", null, LocaleContextHolder.getLocale()));
        setHttpStatus(HttpStatus.OK);
    }

    public void setRegisterSuccessful(){
        setResponseCode("0");
        setSuccess(true);
        setMessage(messageSource.getMessage("register.success", null, LocaleContextHolder.getLocale()));
        setHttpStatus(HttpStatus.OK);
    }

    public void setUserAlreadyExist(){
        setResponseCode("1");
        setSuccess(false);
        setMessage(messageSource.getMessage("register.fail.phoneAlreadyExist", null, LocaleContextHolder.getLocale()));
        setHttpStatus(HttpStatus.BAD_REQUEST);
    }


}
