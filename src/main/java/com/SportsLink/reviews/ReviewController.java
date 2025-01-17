package com.SportsLink.reviews;

import com.SportsLink.reviews.requests.CreateReviewRequest;
import com.SportsLink.utils.GenericResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sportsLink/api/v1/review")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@Tag(name = "Reviews" , description = "Apis That is Responsible Reviews and Field Ratings")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/createReview")
    ResponseEntity<?> createFieldReview(@Valid @RequestBody CreateReviewRequest request,
                                                      HttpServletRequest httpServletRequest,
                                                      @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage,
                                                      BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new GenericResponse(errorMessage));
        }

        return reviewService.createReview(request,acceptLanguage,httpServletRequest);
    }


    @GetMapping("/getFieldReviews/{fieldId}")
    ResponseEntity<GenericResponse> getFieldReviews(@PathVariable int fieldId){
        return reviewService.getFieldReviews(fieldId);
    }
}
