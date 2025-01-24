package com.SportsLink.hourlySlot;


import com.SportsLink.pitch.dto.PitchWithSlotsDTO;
import com.SportsLink.pitch.dto.SlotDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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


    @GetMapping("/slots")
    public ResponseEntity<List<SlotDTO>> getAllSlotsForPitch(
            @RequestParam int pitchId,
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate,
            HttpServletRequest request) {
        return ResponseEntity.ok(hourlySlotService.getAllSlotsForPitch(pitchId, startDate, endDate,request));
    }


    //TODO: Get All Slots (Available or Booked or Blocked) for a Date Range

}
