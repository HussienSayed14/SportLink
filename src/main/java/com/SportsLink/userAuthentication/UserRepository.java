package com.SportsLink.userAuthentication;

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

    @Transactional
    @Modifying
    @Query(value = "UPDATE UserModel u SET u.is_verified= true WHERE u.user_id =:userId" )
    void verifyUser(int userId);


    @Query(value = "SELECT u.phone_number FROM UserModel WHERE u.user_id =:userId")
    String getUserPhoneById(int userId);
}
