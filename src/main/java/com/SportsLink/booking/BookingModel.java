package com.SportsLink.booking;

import com.SportsLink.fields.FieldModel;
import com.SportsLink.horlySlot.HourlySlotModel;
import com.SportsLink.pitch.PitchModel;
import com.SportsLink.userAuthentication.UserModel;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "booking", indexes = {

//        @Index(name = "idx_city_hour_price", columnList = "city_id, hour_price")
})
public class BookingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int numOfBookedHours;

    private Timestamp creationDate;
    private float totalPrice;
    private BookingStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private FieldModel field;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pitch_id")
    private PitchModel pitch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_hour_id")
    private HourlySlotModel startHour;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_hour_id")
    private HourlySlotModel endHour;







}
