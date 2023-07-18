package com.example.user_service.externalservice;

import com.example.user_service.model.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Service    //only for testing
@FeignClient(name="RATING-SERVICE")
public interface RatingService {
    @GetMapping("ratings/users/{userId}")
    public List<Rating> getRatingByUserId(@PathVariable String userId) ;

    //only for testing
    @PostMapping("/ratings/add_new_rating")
    public Rating createRating(Rating values);

}
