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

    //TODO: Get Available Slots for a Specific Pitch (Filter By Date)
    //TODO: Get All Slots (Available or Booked or Blocked) for a Date Range

    /*TODO
    Get Daily Summary of Slots
Description: Retrieve a summary of all slots for a specific date for a pitch and field, categorized by status.

Endpoint:

http
Copy
Edit
GET /api/hourly-slots/summary
Query Parameters:

fieldId (required): The ID of the field.
pitchId (required): The ID of the pitch.
date (required): The date for which the summary is needed.
Response Example:

json
Copy
Edit
{
  "date": "2025-01-18",
  "totalSlots": 24,
  "availableSlots": 20,
  "bookedSlots": 4
}
     */

}
