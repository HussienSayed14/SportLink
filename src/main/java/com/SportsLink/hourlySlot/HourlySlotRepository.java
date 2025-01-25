package com.SportsLink.hourlySlot;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;


public interface HourlySlotRepository extends JpaRepository<HourlySlotModel, Integer> {

    @Query("""
         SELECT hs.id AS id, hs.slotDate AS slotDate, hs.startTime AS startTime, hs.endTime AS endTime, hs.status AS status
        FROM HourlySlotModel hs
        WHERE hs.pitch.id = :pitchId
        AND hs.slotDate BETWEEN :startDate AND :endDate
        ORDER BY hs.slotDate, hs.startTime
    """)
    List<SlotProjection> findSlotsByPitchIdAndDateRange(
             int pitchId,
             Date startDate,
             Date endDate
    );

    @Query("""
        SELECT hs.id AS id, hs.slotDate AS slotDate, hs.startTime AS startTime, hs.endTime AS endTime, hs.status AS status
        FROM HourlySlotModel hs
        WHERE hs.pitch.id = :pitchId
          AND hs.slotDate BETWEEN :startDate AND :endDate
          AND (hs.status = 'AVAILABLE' OR hs.status = 'PENDING'
        ORDER BY hs.slotDate, hs.startTime
    """)
    List<SlotProjection> findAvailableSlotsByPitchIdAndDateRange(
            int pitchId,
            Date startDate,
            Date endDate
    );

    @Modifying
    @Transactional
    @Query("""
    UPDATE HourlySlotModel hs
    SET hs.status = 'PENDING'
    WHERE hs.id IN :slotIds AND hs.status = 'AVAILABLE'
    """)
    int markSlotsAsPending(List<Integer> slotIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT hs FROM HourlySlotModel hs WHERE hs.id IN :slotIds")
    List<HourlySlotModel> findAvailableSlotsWithLock(List<Integer> slotIds);

}
