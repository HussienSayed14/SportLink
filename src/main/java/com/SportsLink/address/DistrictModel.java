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
@Table(name = "district")
public class DistrictModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int district_id;
    @Column(length = 40)
    private String name_en;
    @Column(length = 40)
    private String name_ar;
    @OneToMany(mappedBy = "district")
    private List<FieldModel> fields;
    @OneToMany(mappedBy = "district")
    private List<UserModel> users;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityModel city;
}
