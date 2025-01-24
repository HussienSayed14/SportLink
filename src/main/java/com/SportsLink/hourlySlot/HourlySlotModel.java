package com.SportsLink.hourlySlot;

import com.SportsLink.booking.BookingModel;
import com.SportsLink.fields.FieldModel;
import com.SportsLink.pitch.PitchModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
@Table(
        name = "hourly_slot",
        indexes = {
                // Uncomment and modify as needed
                // @Index(name = "idx_city_hour_price", columnList = "city_id, hour_price")
        }
)
public class HourlySlotModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "slot_date", nullable = false)
    @NotNull(message = "Slot date cannot be null")
    private Date slotDate;

    @Column(name = "start_time", nullable = false)
    @NotNull(message = "Start time cannot be null")
    private Time startTime;

    @Column(name = "end_time", nullable = false)
    @NotNull(message = "End time cannot be null")
    private Time endTime;

    @Column(name = "hour_price", nullable = false)
    @Min(value = 0, message = "Hour price must be greater than or equal to 0")
    private float hourPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status cannot be null")
    private SlotStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    private FieldModel field;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pitch_id", nullable = false)
    private PitchModel pitch;


    @OneToMany(mappedBy = "startHour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingModel> bookingStartHour;

    @OneToMany(mappedBy = "endHour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingModel> bookingEndHour;
}
