package com.SportsLink.userAuthentication.verification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VerificationRepository extends JpaRepository<VerificationModel,Integer> {

    @Query("SELECT v FROM VerificationModel v WHERE v.user_id =:userId")
    VerificationModel getVerificationRecordByUserId(int userId);
}
