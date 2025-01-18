package com.SportsLink.promocode;


import com.SportsLink.fields.FieldModel;
import com.SportsLink.payment.PaymentModel;
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
@Table(name = "promo_code_usage", indexes = {

//        @Index(name = "idx_city_hour_price", columnList = "city_id, hour_price")
})
public class PromoCodeUsageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "promo_code_id", nullable = false)
    private PromoCodeModel promoCode;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private PaymentModel payment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private FieldModel field;

    @Column(nullable = false)
    private Timestamp usageDate;
}
