package com.SportsLink.hourlySlot;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HourlySlotService {
    private static final Logger logger = LoggerFactory.getLogger(HourlySlotService.class);
    private final HourlySlotRepository hourlySlotRepository;

    public List<SlotProjection> getAllSlotsForPitch(int pitchId, Date startDate, Date endDate, HttpServletRequest request) {

        try {
            if (startDate == null) {
                startDate = new Date(System.currentTimeMillis());
            }
            if (endDate == null) {
                endDate = new Date(startDate.getTime() + 7 * 24 * 60 * 60 * 1000); // Default 7 days
            }


                List<SlotProjection> slots = hourlySlotRepository.findSlotsByPitchIdAndDateRange(
                        pitchId, startDate, endDate
                );

            return slots;

        }catch (Exception e){
            logger.error("An Error happened while getAllSlotsForPitch for field: " + pitchId);
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void lockSlotsForBooking(List<Integer> slotIds) {
        List<HourlySlotModel> slots = hourlySlotRepository.findSlotsWithLock(slotIds);


        for(HourlySlotModel slot : slots){
            if(slot.getStatus() != SlotStatus.AVAILABLE){
                //TODO: set the response that the slot is not available.
                return;
            }
            slot.setStatus(SlotStatus.PENDING);
        }

        hourlySlotRepository.saveAll(slots);
    }

    @Transactional
    public void releaseLockedSlots(List<Integer> slotIds) {
        List<HourlySlotModel> slots = hourlySlotRepository.findPendingSlotsWithLock(slotIds);

        for (HourlySlotModel slot : slots) {
            slot.setStatus(SlotStatus.AVAILABLE);
        }

        hourlySlotRepository.saveAll(slots);
    }
}
