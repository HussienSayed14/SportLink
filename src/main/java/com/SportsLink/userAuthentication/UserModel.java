package com.SportsLink.userAuthentication;


import com.SportsLink.fields.FieldModel;
import com.SportsLink.address.CityModel;
import com.SportsLink.address.DistrictModel;
import com.SportsLink.address.GovernoratesModel;
import com.SportsLink.loginAudit.LoginAuditModel;
import com.SportsLink.smsLimit.SmsDailyLimitModel;
import com.SportsLink.userAuthentication.forgotPassword.ForgotPasswordModel;
import com.SportsLink.userAuthentication.verification.VerificationModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users", indexes = {
        @Index(name = "idx_phone_number",columnList = "phone_number",unique = true)
})

public class UserModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    @NotNull(message = "user phone cannot be null")
    @Size(min = 8, max = 15)
    @Column(name = "phone_number", length = 15, nullable = false)
    private String phone_number;
    @Email
    @Column(name = "email", length = 120)
    private String email;
    @NotNull(message = "Name cannot be null")
    @Size(max = 100, message = "Name must be less than or equal to 100 characters")
    @Column(name = "name",length = 100, nullable = false)
    private String name;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private RolesEnum role;
    @NotNull
    @Column(name = "password_hash", nullable = false)
    private String password_hash;
    private int failed_attempts = 0;
    private boolean is_blocked = false;
    @Column(name = "blocked_until", nullable = true)
    private Timestamp blocked_until = null;
    private boolean is_verified = false;
    private boolean is_activated = true;
    private Timestamp created_at;
    private Timestamp last_login = null;
    @JsonIgnore
    @OneToMany(mappedBy = "user_id",fetch = FetchType.LAZY)
    private List<LoginAuditModel> loginAudits;
    @JsonIgnore
    @OneToMany(mappedBy = "fieldOwner",fetch = FetchType.LAZY)
    private List<FieldModel> ownedFields;
    @JsonIgnore
    @OneToOne(mappedBy = "user_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    VerificationModel userVerification;
    @OneToOne(mappedBy = "user_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private ForgotPasswordModel forgotPasswordRecord;
    @JsonIgnore
    @OneToMany(mappedBy = "user_id",fetch = FetchType.LAZY)
    private List<SmsDailyLimitModel> dailyLimits;

    @OneToOne
    @JoinColumn(name = "governorate_id", referencedColumnName = "governorate_id")
    private GovernoratesModel governorate;

    @OneToOne
    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    private CityModel city;

    @OneToOne
    @JoinColumn(name = "district_id", referencedColumnName = "district_id")
    private DistrictModel district;





    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return password_hash;
    }

    @Override
    public String getUsername() {
        return phone_number;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
