package com.SportsLink.pitch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PitchRepository extends JpaRepository<PitchModel, Integer> {

    @Query(value = """
            SELECT p
            FROM PitchModel p
            WHERE p.field.id = :fieldId
            """)
    List<PitchModel> findPitchesByFieldId(int fieldId);
}
