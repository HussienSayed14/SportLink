package com.SportsLink.promocode;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "promo_code", indexes = {

//        @Index(name = "idx_city_hour_price", columnList = "city_id, hour_price")
})
public class PromoCodeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;

    private Date validFrom;
    private Date validTo;
    private int maxUsage;
    private int usedCount;
    private boolean isActive;

    private PromoType promoType;
    private float amount;

    @OneToMany(mappedBy = "promoCode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromoCodeFieldModel> promoCodeFields;

}
