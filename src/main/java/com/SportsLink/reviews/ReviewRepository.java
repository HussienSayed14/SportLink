package com.SportsLink.reviews;

import com.SportsLink.reviews.projection.FieldReviewsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewModel, Integer> {

    @Query("SELECT ROUND(AVG(r.rating), 1) FROM ReviewModel r WHERE r.field.id = :fieldId")
    Double findAverageRatingByFieldId(int fieldId);


    @Query("SELECT r.id as id, r.user.name as name, r.rating as rating, r.reviewText as text, r.createdAt as timestamp " +
            "FROM ReviewModel r WHERE r.field.id =:fieldId")
    List<FieldReviewsProjection> getReviewsByFieldId(int fieldId);

}
