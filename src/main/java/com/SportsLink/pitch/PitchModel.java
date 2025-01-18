package com.SportsLink.pitch;

import com.SportsLink.booking.BookingModel;
import com.SportsLink.fields.FieldModel;
import com.SportsLink.hourlySlot.HourlySlotModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(
        name = "pitch",
        indexes = {
                // Uncomment and modify as needed
                // @Index(name = "idx_field_pitch_type", columnList = "field_id, pitch_type")
        }
)
public class PitchModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pitch_number", nullable = false)
    @NotNull(message = "Pitch number cannot be null")
    @Min(value = 1, message = "Pitch number must be greater than or equal to 1")
    private int pitchNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "pitch_type", nullable = false)
    @NotNull(message = "Pitch type cannot be null")
    private PitchType pitchType;

    @Column(name = "number_of_players")
    private int numberOfPlayers;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "allow_booking", nullable = false)
    private boolean allowBooking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    @NotNull(message = "Field cannot be null")
    private FieldModel field;

    @OneToMany(mappedBy = "pitch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HourlySlotModel> hourlySlots;

    @OneToMany(mappedBy = "pitch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingModel> booking;
}
