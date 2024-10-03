package com.SportsLink.userAccount.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    @Query(value = "SELECT u FROM UserModel u WHERE u.phone_number = :phone_number")
    Optional<UserModel> findOptionalUserByPhoneNumber(String phone_number);

    @Query(value = "SELECT u FROM UserModel u WHERE u.phone_number = :phone_number")
    UserModel findUserByPhoneNumber(String phone_number);
}
