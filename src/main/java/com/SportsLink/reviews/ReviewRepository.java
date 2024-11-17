package com.SportsLink.reviews;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<ReviewModel, Integer> {

    @Query("SELECT AVG(r.rating) FROM ReviewModel r WHERE r.field.id = :fieldId")
    Double findAverageRatingByFieldId(int fieldId);

}
