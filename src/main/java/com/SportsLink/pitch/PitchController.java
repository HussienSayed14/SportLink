package com.SportsLink.pitch;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sportsLink/api/v1/pitch")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@Tag(name = "Pitch" , description = "Apis That are responsible for field hourly slots")
public class PitchController {

    private final PitchService pitchService;


    //TODO: Get Detailed Slots for a Specific Pitch, Retrieve all slots (available and booked) for a specific pitch within a custom date range. (getSlotsForPitch)


}
