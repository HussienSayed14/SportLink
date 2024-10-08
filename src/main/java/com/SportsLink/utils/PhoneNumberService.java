package com.SportsLink.utils;


public class PhoneNumberService {

    public static String formatPhoneNumber(String countryCode, String phoneNumber){
        countryCode = countryCode.trim();
        phoneNumber = phoneNumber.trim();
        if (phoneNumber.startsWith("0")) {
            phoneNumber = phoneNumber.substring(1);
        }
        return countryCode + phoneNumber;
    }
}
