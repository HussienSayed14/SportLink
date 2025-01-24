package com.SportsLink.pitch.dto;

import com.SportsLink.hourlySlot.HourlySlotModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PitchWithSlotsDTO {
    private int pitchId;
    private String pitchType;
    private int numberOfPlayers;
    private boolean isActive;
    private int pitchNumber;
    private boolean allowBooking;
    private float hourPrice;
    private List<SlotDTO> slots;
}
