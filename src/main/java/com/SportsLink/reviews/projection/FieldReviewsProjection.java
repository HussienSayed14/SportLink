package com.SportsLink.reviews.projection;


import java.time.LocalDateTime;

public interface FieldReviewsProjection {

    int getId();
    String getName();
    int getRating();
    String getText();
    LocalDateTime getTimestamp();
}
