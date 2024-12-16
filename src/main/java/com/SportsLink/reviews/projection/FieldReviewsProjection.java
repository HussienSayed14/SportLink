package com.SportsLink.reviews.projection;

import java.sql.Timestamp;

public interface FieldReviewsProjection {

    int getId();
    String getName();
    int getRating();
    String getText();
    Timestamp getTimestamp();
}
