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

    @GetMapping("/getAll")
    ResponseEntity<?> getAllFieldPitches(@RequestParam int fieldId, HttpServletRequest request){
        return pitchService.getAllFieldPitches(fieldId, request);
    }

    //TODO: Get Pitches with their available hours for a date range.

    //TODO:

}
