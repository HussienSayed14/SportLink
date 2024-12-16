package com.SportsLink.reviews.response;

import com.SportsLink.reviews.projection.FieldReviewsProjection;
import com.SportsLink.utils.GenericResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FieldReviewsResponse extends GenericResponse {
    List<FieldReviewsProjection> reviewsList;
}
