package com.SportsLink.hourlySlot;



import com.SportsLink.hourlySlot.requests.SlotLockRequest;
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
    public ResponseEntity<List<SlotProjection>> getAllSlotsForPitch(
            @RequestParam int pitchId,
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate,
            HttpServletRequest request) {
        return ResponseEntity.ok(hourlySlotService.getAllSlotsForPitch(pitchId, startDate, endDate,request));
    }

    // Lock slots for booking
    @PostMapping("/lock-slots")
    public ResponseEntity<String> lockSlotsForBooking(@RequestBody SlotLockRequest request) {
        try {
            hourlySlotService.lockSlotsForBooking(request.getSlotIds());
            return ResponseEntity.ok("Slots locked successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Release locked slots
    @PostMapping("/release-slots")
    public ResponseEntity<String> releaseLockedSlots(@RequestBody SlotLockRequest request) {
        hourlySlotService.releaseLockedSlots(request.getSlotIds());
        return ResponseEntity.ok("Slots released successfully.");
    }

}
