package com.SportsLink.pitch;


import com.SportsLink.booking.BookingModel;
import com.SportsLink.fields.FieldModel;
import com.SportsLink.hourlySlot.HourlySlotModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "pitch", indexes = {

//        @Index(name = "idx_city_hour_price", columnList = "city_id, hour_price")
})
public class PitchModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pitch_number", nullable = false)
    private int pitchNumber;

    private PitchType pitchType;

    private int numberOfPlayers;

    private boolean isActive;
    private boolean allowBooking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private FieldModel field;


    @OneToMany(mappedBy = "pitch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HourlySlotModel> hourlySlots;

    @OneToMany(mappedBy = "pitch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingModel> booking;


}
