package com.SportsLink.address;


import com.SportsLink.fields.FieldModel;
import com.SportsLink.userAuthentication.UserModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "governorate")
public class GovernoratesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int governorate_id;
    @Column(length = 40)
    private String name_en;
    @Column(length = 40)
    private String name_ar;
    @OneToOne(mappedBy = "governorate")
    private FieldModel field;
    @OneToOne(mappedBy = "governorate")
    private UserModel user;
    @OneToMany(mappedBy = "governorate",fetch = FetchType.LAZY)
    private List<CityModel> cities;

}
