package com.SportsLink.utils;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);


    @Async
    public void sendSmsMessage(String phoneNumber, String message){
        try {
            // To be implemented using Twilio
            logger.info("An sms is sent to: " + phoneNumber);

        }catch (Exception e){
            logger.error("An Error happened while sending sms to: "+ phoneNumber + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
