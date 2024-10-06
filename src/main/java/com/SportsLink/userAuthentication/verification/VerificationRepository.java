package com.SportsLink.userAuthentication.verification;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VerificationRepository extends JpaRepository<VerificationModel,Integer> {

    @Query(value = "SELECT * FROM user_verification  WHERE user_id =:userId",nativeQuery = true)
    VerificationModel getVerificationRecordByUserId(int userId);

    @Transactional
    @Modifying
    @Query("UPDATE VerificationModel v SET v.attempt_count = v.attempt_count + 1 WHERE v.verification_id = :verification_id")
    void incrementAttemptById(int verification_id);
}
