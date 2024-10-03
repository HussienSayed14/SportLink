package com.SportsLink.utils;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Service
public class DateTimeService {

    public Timestamp getCurrentTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    public Date getCurrentDate(){
        return new Date(System.currentTimeMillis());
    }

    public Time getCurrentTime(){
        return new Time(System.currentTimeMillis());
    }
}
