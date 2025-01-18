package com.SportsLink.booking;

import com.SportsLink.fields.FieldModel;
import com.SportsLink.hourlySlot.HourlySlotModel;
import com.SportsLink.payment.PaymentModel;
import com.SportsLink.pitch.PitchModel;
import com.SportsLink.userAuthentication.UserModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

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

    @Column(name = "number_of_booked_hours", nullable = false)
    @Min(value = 1, message = "Number of booked hours must be at least 1")
    private int numberOfBookedHours;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @NotNull(message = "Creation date cannot be null")
    private Timestamp creationDate;

    @Column(name = "total_price", nullable = false)
    @Min(value = 0, message = "Total price must be at least 0")
    private float totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status cannot be null")
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

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentModel> payments;








}
