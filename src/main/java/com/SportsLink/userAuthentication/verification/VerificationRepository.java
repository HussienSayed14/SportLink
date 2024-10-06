package com.SportsLink.userAuthentication.verification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<VerificationModel,Integer> {
}
