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
}
