package com.SportsLink.reviews;

import com.SportsLink.address.GovernoratesModel;
import com.SportsLink.config.JwtService;
import com.SportsLink.fields.FieldModel;
import com.SportsLink.fields.FieldRepository;
import com.SportsLink.reviews.requests.CreateReviewRequest;
import com.SportsLink.userAuthentication.UserModel;
import com.SportsLink.utils.GenericResponse;
import com.SportsLink.utils.MessageService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ReviewService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    private final ReviewRepository reviewRepository;
    private final MessageService messageService;
    private final JwtService jwtService;
    private final EntityManager entityManager;
    private final ReviewAsyncService reviewAsyncService;


    public ResponseEntity<GenericResponse> createReview(CreateReviewRequest request,
                                                        String acceptLanguage,
                                                        HttpServletRequest httpServletRequest) {

        GenericResponse response = new GenericResponse();
        try {
            int userId = jwtService.extractUserIdFromCookie(httpServletRequest);


            ReviewModel review = new ReviewModel();
            review.setField(entityManager.getReference(FieldModel.class, request.getFieldId()));
            review.setRating(request.getRating());
            review.setReviewText(request.getComment());
            review.setUser(entityManager.getReference(UserModel.class, userId));

            reviewRepository.save(review);

            reviewAsyncService.updateAverageRating(review.getField());

        }catch (Exception e){
            response.setServerError(messageService.getMessage("unexpected.error"));
            logger.error("An Error happened while creating review for Field \n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }


}
