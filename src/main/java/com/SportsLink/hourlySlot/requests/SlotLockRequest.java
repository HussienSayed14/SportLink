package com.SportsLink.hourlySlot.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SlotLockRequest {
    List<Integer> slotIds;
}
