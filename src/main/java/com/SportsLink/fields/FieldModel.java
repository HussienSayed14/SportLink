package com.SportsLink.fields;

import com.SportsLink.address.CityModel;
import com.SportsLink.address.DistrictModel;
import com.SportsLink.address.GovernoratesModel;
import com.SportsLink.userAuthentication.UserModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "field", indexes = {
        @Index(name = "idx_city",columnList = "city_id"),
        @Index(name = "idx_district",columnList = "district_id"),
        @Index(name = "idx_hour_price", columnList = "hour_price"),
        @Index(name = "idx_is_blocked", columnList = "is_blocked"),
        @Index(name = "idx_city_hour_price", columnList = "city_id, hour_price")
})
public class FieldModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int field_id;

    @ManyToOne
    @JoinColumn(name = "field_owner_id")
    private UserModel fieldOwner;

    @Column(name = "field_name_en", nullable = false, length = 100)
    private String fieldNameEn;

    @Column(name = "field_name_ar", nullable = false, length = 100)
    private String fieldNameAr;

    @Column(name = "land_mark_en", length = 150)
    private String landMarkEn;

    @Column(name = "land_mark_ar", length = 150)
    private String landMarkAr;

    @Column(name = "hour_price", nullable = false)
    private float hourPrice;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;

    @Column(name = "google_maps_location", nullable = false, length = 255)
    private String googleMapsLocation;

    @OneToOne
    @JoinColumn(name = "governorate_id", referencedColumnName = "governorate_id")
    private GovernoratesModel governorate;

    @OneToOne
    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    private CityModel city;

    @OneToOne
    @JoinColumn(name = "district_id", referencedColumnName = "district_id")
    private DistrictModel district;

}
