package com.SportsLink.hourlySlot;

import java.sql.Date;
import java.sql.Time;

public interface SlotProjection {

    int getId();
    Date getSlotDate();
    Time getStartTime();
    Time getEndTime();
    String getStatus();
}
