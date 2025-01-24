package com.SportsLink.hourlySlot;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/sportsLink/api/v1/hourly-slots")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@Tag(name = "Hourly Slots" , description = "Apis That are responsible for field hourly slots")
public class HourlySlotsController {


    private final HourlySlotService hourlySlotService;


    //TODO
    @GetMapping
    public ResponseEntity<List<HourlySlotModel>> getHourlySlots(
            @RequestParam int fieldId,
            @RequestParam int pitchId) {
        return ResponseEntity.ok(hourlySlotService.getHourlySlotsByFieldAndPitch(fieldId, pitchId));
    }


    //TODO
    @GetMapping("/date")
    public ResponseEntity<List<HourlySlotModel>> getHourlySlotsByDate(
            @RequestParam int fieldId,
            @RequestParam int pitchId,
            @RequestParam Date slotDate) {
        return ResponseEntity.ok(hourlySlotService.getPitchHourlySlotsByDate(fieldId, pitchId, slotDate));
    }
    //TODO: Get Detailed Slots for a Specific Pitch, Retrieve all slots (available and booked) for a specific pitch within a custom date range. (getSlotsForPitch)


}
