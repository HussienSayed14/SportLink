package com.SportsLink.promocode;

import com.SportsLink.fields.FieldModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "promo_code_field", indexes = {

//        @Index(name = "idx_city_hour_price", columnList = "city_id, hour_price")
})
public class PromoCodeFieldModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "promo_code_id", nullable = false)
    private PromoCodeModel promoCode;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private FieldModel field;



}
