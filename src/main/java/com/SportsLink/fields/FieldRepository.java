package com.SportsLink.fields;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FieldRepository extends JpaRepository<FieldModel, Integer>, JpaSpecificationExecutor {

    @Modifying
    @Transactional
    @Query("UPDATE FieldModel f SET f.followersCount = f.followersCount - 1 WHERE f.id = :fieldId")
    void decrementFieldFollowers(int fieldId);

    @Modifying
    @Transactional
    @Query("UPDATE FieldModel f SET f.followersCount = f.followersCount + 1 WHERE f.id = :fieldId")
    void incrementFieldFollowers(int fieldId);
}
