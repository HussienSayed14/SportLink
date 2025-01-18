package com.SportsLink.fields;

import com.SportsLink.address.CityModel;
import com.SportsLink.address.DistrictModel;
import com.SportsLink.address.GovernoratesModel;
import com.SportsLink.booking.BookingModel;
import com.SportsLink.followers.FollowerModel;
import com.SportsLink.horlySlot.HourlySlotModel;
import com.SportsLink.notification.NotificationModel;
import com.SportsLink.pitch.PitchModel;
import com.SportsLink.promocode.PromoCodeFieldModel;
import com.SportsLink.reviews.ReviewModel;
import com.SportsLink.userAuthentication.UserModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "field", indexes = {
        @Index(name = "idx_city", columnList = "city_id"),
        @Index(name = "idx_district", columnList = "district_id"),
        @Index(name = "idx_hour_price", columnList = "hour_price"),
        @Index(name = "idx_is_blocked", columnList = "is_blocked"),
        @Index(name = "idx_city_hour_price", columnList = "city_id, hour_price")
})
public class FieldModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @ManyToOne
    @JoinColumn(name = "governorate_id")
    private GovernoratesModel governorate;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityModel city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private DistrictModel district;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewModel> reviews;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "followers_count")
    private int followersCount;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FollowerModel> followers;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationModel> notification;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PitchModel> pitches;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HourlySlotModel> hourlySlots;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingModel> booking;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromoCodeFieldModel> promoCodeFields;

}
