package com.SportsLink.utils;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class DateTimeService {

    public Timestamp getCurrentTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    public Timestamp addMinutesToNow(int minutes) {
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(minutes);
        return Timestamp.valueOf(localDateTime);
    }

    public Timestamp addHoursToNow(int hours) {
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(hours);
        return Timestamp.valueOf(localDateTime);
    }

    public Timestamp addDaysToNow(int days) {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(days);
        return Timestamp.valueOf(localDateTime);
    }

    public Date getCurrentDate(){
        return new Date(System.currentTimeMillis());
    }

    public Time getCurrentTime(){
        return new Time(System.currentTimeMillis());
    }

    /**
     * Checks if the request is still valid based on the creationDate and expireDate.
     *
     * @param creationDate the timestamp when the request was created
     * @param expireDate   the timestamp when the request expires
     * @return true if the request is valid, false if expired or invalid
     */
    public boolean isRequestNonExpired(Timestamp creationDate, Timestamp expireDate) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        // Check if creationDate is after expireDate (invalid data)
        if (creationDate.after(expireDate)) {
            
            return false;
        }

        // Check if current time is after expireDate (request expired)
        if (now.after(expireDate)) {
            return false;
        }

        // The request is valid
        return true;
    }

    public boolean isRequestBlocked(Timestamp blockDate) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        // Check if current time is after Block date (request not blocked)
        if (now.after(blockDate)) {
            return false;
        }

        // The request is blocked
        return true;
    }
}
