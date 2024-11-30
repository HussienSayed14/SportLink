package com.SportsLink.userAuthentication;

import com.SportsLink.userData.dtos.UserDetailsDto;
import com.SportsLink.userData.dtos.UserDetailsProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    @Query(value = "SELECT u FROM UserModel u WHERE u.phone_number = :phone_number")
    Optional<UserModel> findOptionalUserByPhoneNumber(String phone_number);

    @Query(value = "SELECT u FROM UserModel u WHERE u.phone_number = :phone_number")
    UserModel findUserByPhoneNumber(String phone_number);

    @Query(value = "SELECT u FROM UserModel u WHERE u.phone_number = :phone_number AND u.is_verified = true")
    UserModel findVerifiedUserByPhoneNumber(String phone_number);


    @Transactional
    @Modifying
    @Query(value = "UPDATE UserModel u SET u.is_verified= true WHERE u.user_id =:userId" )
    void verifyUser(int userId);


    @Query(value = "SELECT u.phone_number FROM UserModel u WHERE u.user_id =:userId")
    String getUserPhoneById(int userId);

    @Query(value = "SELECT u FROM UserModel u WHERE u.user_id =:userId")
    UserModel getUserById(int userId);

    @Query(value = "SELECT u.user_id as id, u.phone_number as phone, u.name as name, u.role as role, u.created_at as timestamp " +
            "FROM UserModel u " +
            "WHERE u.user_id =:userId")
    UserDetailsProjection getUserDetailsById(int userId);
}
