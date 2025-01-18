package com.SportsLink.payment;

import com.SportsLink.booking.BookingModel;
import com.SportsLink.userAuthentication.UserModel;
import com.SportsLink.promocode.PromoCodeUsageModel;
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
@Table(
        name = "payment",
        indexes = {
                // Uncomment and customize as needed
                // @Index(name = "idx_city_hour_price", columnList = "city_id, hour_price")
        }
)
public class PaymentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "payment_date", nullable = false, updatable = false)
    @NotNull(message = "Payment date cannot be null")
    private Timestamp paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    @NotNull(message = "Payment status cannot be null")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    @NotNull(message = "Payment method cannot be null")
    private PaymentMethod paymentMethod;

    @Column(name = "original_price", nullable = false)
    @Min(value = 0, message = "Original price must be greater than or equal to 0")
    private float originalPrice;

    @Column(name = "promo_code_discount", nullable = false)
    @Min(value = 0, message = "Promo code discount must be greater than or equal to 0")
    private float promoCodeDiscount;

    @Column(name = "commission_applied", nullable = false)
    @Min(value = 0, message = "Commission applied must be greater than or equal to 0")
    private float commissionApplied;

    @Column(name = "offer_discount", nullable = false)
    @Min(value = 0, message = "Offer discount must be greater than or equal to 0")
    private float offerDiscount;

    @Column(name = "final_price", nullable = false)
    @Min(value = 0, message = "Final price must be greater than or equal to 0")
    private float finalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    @NotNull(message = "Booking cannot be null")
    private BookingModel booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User cannot be null")
    private UserModel user;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromoCodeUsageModel> promoCodeUsage;
}
