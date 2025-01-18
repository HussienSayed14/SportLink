package com.SportsLink.promocode;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(
        name = "promo_code",
        indexes = {
                // Uncomment and customize as needed
                // @Index(name = "idx_code_validity", columnList = "code, valid_from, valid_to")
        }
)
public class PromoCodeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    @NotNull(message = "Promo code cannot be null")
    @Size(max = 50, message = "Promo code cannot exceed 50 characters")
    private String code;

    @Column(name = "valid_from", nullable = false)
    @NotNull(message = "Valid from date cannot be null")
    private Date validFrom;

    @Column(name = "valid_to")
    private Date validTo;

    @Column(name = "max_usage", nullable = false)
    @Min(value = 1, message = "Max usage must be greater than or equal to 1")
    private int maxUsage;

    @Column(name = "used_count", nullable = false)
    @Min(value = 0, message = "Used count must be greater than or equal to 0")
    private int usedCount;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "promo_type", nullable = false)
    @NotNull(message = "Promo type cannot be null")
    private PromoType promoType;

    @Column(name = "amount", nullable = false)
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    private float amount;

    @OneToMany(mappedBy = "promoCode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromoCodeFieldModel> promoCodeFields;

    @OneToMany(mappedBy = "promoCode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromoCodeUsageModel> promoCodeUsage;
}
