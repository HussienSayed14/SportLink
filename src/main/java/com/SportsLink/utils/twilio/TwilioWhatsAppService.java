package com.SportsLink.utils.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TwilioWhatsAppService {


    private final Environment environment;

    public String ACCOUNT_SID;
    public String AUTH_TOKEN;
    public String SENDER;

    // Constructor-based injection of the Environment object
    public TwilioWhatsAppService(Environment environment) {
        this.environment = environment;
        // Initialize the ACCOUNT_SID after the environment is injected
        this.ACCOUNT_SID = environment.getProperty("twilio.account.sid");
        this.AUTH_TOKEN = environment.getProperty("twilio.auth.token");
        this.SENDER = environment.getProperty("twilio.sender");
    }




    @Async
    public void sendWhatsAppVerificationMessage(String toNumber,String verificationCode, String templateId){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:" + toNumber),
                        new com.twilio.type.PhoneNumber("whatsapp:" + SENDER),
                        templateId)// Content SID of the template
                .setContentSid(templateId)
                .setContentVariables("{\"1\":\"" + verificationCode + "\"}")
                .create();

        System.out.println(message.getBody());

    }

    @Async
    public void sendWhatsAppForgotPasswordMessage(String toNumber,String url, String templateId){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:" + toNumber),
                        new com.twilio.type.PhoneNumber("whatsapp:" + SENDER),
                        templateId)// Content SID of the template
                .setContentSid(templateId)
                .setContentVariables("{\"1\":\"" + url + "\"}")
                .create();

        System.out.println(message.getBody());

    }


}
