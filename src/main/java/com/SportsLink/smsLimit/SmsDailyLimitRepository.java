package com.SportsLink.smsLimit;

import com.SportsLink.userAuthentication.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SmsDailyLimitRepository extends JpaRepository<SmsDailyLimitModel,Integer> {

    @Query(value = "SELECT r FROM SmsDailyLimitModel r WHERE r.user_id =:user AND r.type =:type")
    SmsDailyLimitModel getUserDailyLimit(UserModel user, String type);

    @Modifying
    @Transactional
    @Query("UPDATE SmsDailyLimitModel r SET r.user_attempts = r.user_attempts + 1 WHERE r.user_id =:user AND r.type =:type")
    void incrementSmsDailyLimit(UserModel user, String type);
}
