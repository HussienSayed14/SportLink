package com.SportsLink.pitch;


import com.SportsLink.hourlySlot.HourlySlotRepository;
import com.SportsLink.hourlySlot.SlotProjection;
import com.SportsLink.pitch.dto.PitchWithSlotsDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PitchService {
    private static final Logger logger = LoggerFactory.getLogger(PitchService.class);
    private final PitchRepository pitchRepository;
    private final HourlySlotRepository hourlySlotRepository;

    public List<PitchWithSlotsDTO> getPitchesWithAvailableSlotsForField(int fieldId,
                                                                        Date startDate,
                                                                        Date endDate,
                                                                        HttpServletRequest request) {


        try {
            if (startDate == null) {
                startDate = new Date(System.currentTimeMillis());
            }
            if (endDate == null) {
                endDate = new Date(startDate.getTime() + 5 * 24 * 60 * 60 * 1000); // Default 5 days
            }

            List<PitchModel> activePitches = pitchRepository.findActivePitchesByFieldId(fieldId);

            List<PitchWithSlotsDTO> result = new ArrayList<>();
            for (PitchModel pitch : activePitches) {
                // Fetch slots for each pitch
                List<SlotProjection> slots = hourlySlotRepository.findAvailableSlotsByPitchIdAndDateRange(
                        pitch.getId(), startDate, endDate
                );

                // Build the DTO
                PitchWithSlotsDTO pitchWithSlotsDTO = new PitchWithSlotsDTO();
                pitchWithSlotsDTO.setSlots(slots);
                pitchWithSlotsDTO.setPitchId(pitch.getId());
                pitchWithSlotsDTO.setPitchNumber(pitch.getPitchNumber());
                pitchWithSlotsDTO.setPitchType(String.valueOf(pitch.getPitchType()));
                pitchWithSlotsDTO.setAllowBooking(pitch.isAllowBooking());
                pitchWithSlotsDTO.setHourPrice(pitch.getHourPrice());
                pitchWithSlotsDTO.setActive(pitch.isActive());
                pitchWithSlotsDTO.setNumberOfPlayers(pitch.getNumberOfPlayers());

                result.add(pitchWithSlotsDTO);
            }

            return result;

        }catch (Exception e){
            logger.error("An Error happened while getPitchesWithAvailableSlotsForField for field: " + fieldId);
            e.printStackTrace();
            return null;
        }


    }
}
