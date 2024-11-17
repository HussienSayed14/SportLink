package com.SportsLink.reviews;

import com.SportsLink.fields.FieldModel;
import com.SportsLink.fields.FieldRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewAsyncService {
    private final ReviewRepository reviewRepository;
    private final FieldRepository fieldRepository;

    @Async
    public void updateAverageRating(FieldModel field) {
        Double averageRating = reviewRepository.findAverageRatingByFieldId(field.getId());
        field.setAverageRating(averageRating);
        fieldRepository.save(field);
    }
}
