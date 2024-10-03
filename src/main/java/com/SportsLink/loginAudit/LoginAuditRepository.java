package com.SportsLink.loginAudit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginAuditRepository extends JpaRepository<LoginAuditModel,Integer> {
}
