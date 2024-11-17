package com.SportsLink.reviews.requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReviewRequest {

    int fieldId;
    int rating;
    String comment;
}
