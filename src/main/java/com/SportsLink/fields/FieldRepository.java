package com.SportsLink.fields;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FieldRepository extends JpaRepository<FieldModel, Integer>, JpaSpecificationExecutor {

    @Modifying
    @Transactional
    @Query("UPDATE FieldModel f SET f.followerCount = :followersCount + 1 WHERE f.id = :fieldId")
    void incrementFollowerCountById(int fieldId, int followersCount);
}
