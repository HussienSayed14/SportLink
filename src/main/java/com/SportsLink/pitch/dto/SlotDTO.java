package com.SportsLink.pitch.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
public class SlotDTO {
    private int slotId;
    private Date slotDate;
    private Time startTime;
    private Time endTime;
    private String status;
}
