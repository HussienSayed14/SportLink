package com.SportsLink.address;

import com.SportsLink.fields.FieldModel;
import com.SportsLink.userAuthentication.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "city")
public class CityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int city_id;
    @Column(length = 40)
    private String nameEn;
    @Column(length = 40)
    private String nameAr;
    @OneToMany(mappedBy = "city")
    private List<FieldModel> fields;
    @OneToMany(mappedBy = "city")
    private List<UserModel> users;
    @ManyToOne
    @JoinColumn(name = "governorate_id")
    GovernoratesModel governorate;
    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private List<DistrictModel> districts;

}
