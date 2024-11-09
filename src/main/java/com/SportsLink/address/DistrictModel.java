package com.SportsLink.address;

import com.SportsLink.fields.FieldModel;
import com.SportsLink.userAuthentication.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DistrictModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int district_id;
    @Column(length = 40)
    private String name_en;
    @Column(length = 40)
    private String name_ar;
    @OneToOne(mappedBy = "district")
    private FieldModel field;
    @OneToOne(mappedBy = "district")
    private UserModel user;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityModel city;
}
