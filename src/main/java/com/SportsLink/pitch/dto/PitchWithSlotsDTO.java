package com.SportsLink.pitch.dto;

import com.SportsLink.hourlySlot.SlotProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PitchWithSlotsDTO {
    private int pitchId;
    private String pitchType;
    private int numberOfPlayers;
    private boolean isActive;
    private int pitchNumber;
    private boolean allowBooking;
    private float hourPrice;
    private List<SlotProjection> slots;
}
