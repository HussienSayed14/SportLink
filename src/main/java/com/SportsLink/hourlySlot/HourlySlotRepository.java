package com.SportsLink.hourlySlot;

import com.SportsLink.pitch.dto.SlotDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;


public interface HourlySlotRepository extends JpaRepository<HourlySlotModel, Integer> {

    @Query("""
        SELECT new com.SportsLink.pitch.dto.SlotDTO(
            hs.id, hs.slotDate, hs.startTime, hs.endTime, hs.status
        )
        FROM HourlySlotModel hs
        WHERE hs.pitch.id = :pitchId
          AND hs.slotDate BETWEEN :startDate AND :endDate
        ORDER BY hs.slotDate, hs.startTime
    """)
    List<SlotDTO> findSlotsByPitchIdAndDateRange(
             int pitchId,
             Date startDate,
             Date endDate
    );

    @Query("""
        SELECT new com.SportsLink.pitch.dto.SlotDTO(
            hs.id, hs.slotDate, hs.startTime, hs.endTime, hs.status
        )
        FROM HourlySlotModel hs
        WHERE hs.pitch.id = :pitchId
          AND hs.slotDate BETWEEN :startDate AND :endDate
          AND hs.status = 'AVAILABLE'
        ORDER BY hs.slotDate, hs.startTime
    """)
    List<SlotDTO> findAvailableSlotsByPitchIdAndDateRange(
            int pitchId,
            Date startDate,
            Date endDate
    );
}
