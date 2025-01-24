package com.SportsLink.pitch;


import com.SportsLink.pitch.dto.PitchWithSlotsDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/sportsLink/api/v1/pitch")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@Tag(name = "Pitch" , description = "Apis That are responsible for field hourly slots")
public class PitchController {

    private final PitchService pitchService;

    //TODO: Get Pitches with Available Slots for Today (or Default Range): getPitchesWithSlotsForField

    @GetMapping("/{fieldId}/pitches-with-slots")
    public ResponseEntity<List<PitchWithSlotsDTO>> getPitchesWithSlots(
            @PathVariable int fieldId,
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate,
            HttpServletRequest request) {
        return ResponseEntity.ok(pitchService.getPitchesWithAvailableSlotsForField(fieldId, startDate, endDate,request));
    }




}
