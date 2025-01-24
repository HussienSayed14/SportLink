package com.SportsLink.pitch;


import com.SportsLink.pitch.dto.PitchWithSlotsDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PitchService {
    public List<PitchWithSlotsDTO> getPitchesWithAvailableSlotsForField(int fieldId,
                                                                        Date startDate,
                                                                        Date endDate,
                                                                        HttpServletRequest request) {


    }
}
