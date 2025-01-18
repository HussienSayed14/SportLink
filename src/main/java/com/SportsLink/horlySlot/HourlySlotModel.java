package com.SportsLink.horlySlot;


import com.SportsLink.booking.BookingModel;
import com.SportsLink.fields.FieldModel;
import com.SportsLink.pitch.PitchModel;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "hourly_slot", indexes = {

//        @Index(name = "idx_city_hour_price", columnList = "city_id, hour_price")
})
public class HourlySlotModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "slot_date", nullable = false)
    private Date slotDate;

    private Time startTime;
    private Time endTime;
    private float hourPrice;
    private SlotStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private FieldModel field;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private PitchModel pitch;

    @OneToMany(mappedBy = "startHour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingModel> bookingStartHour;

    @OneToMany(mappedBy = "endHour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingModel> bookingEndHour;
}
