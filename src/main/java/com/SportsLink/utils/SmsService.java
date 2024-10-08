package com.SportsLink.utils;

import com.SportsLink.smsLimit.SmsDailyLimitService;
import com.SportsLink.utils.twilio.TwilioWhatsAppService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
    private final TwilioWhatsAppService twilioWhatsAppService;


    @Async
    public void sendWhatsAppVerificationMessage(String phoneNumber, String verificationCode, String templateId){
        try {
            twilioWhatsAppService.sendWhatsAppVerificationMessage(phoneNumber,verificationCode,templateId);
            logger.info("A verification code is sent to: " + phoneNumber + ": " + verificationCode);

        }catch (Exception e){
            logger.error("An Error happened while sending sms to: "+ phoneNumber + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendWhatsAppForgotPasswordMessage(String phoneNumber, String url, String templateId){
        try {
            twilioWhatsAppService.sendWhatsAppForgotPasswordMessage(phoneNumber,url,templateId);
            logger.info("A Forgot Password url is sent to: " + phoneNumber + ": " + url);

        }catch (Exception e){
            logger.error("An Error happened while sending sms to: "+ phoneNumber + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
