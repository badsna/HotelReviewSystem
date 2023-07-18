package com.example.user_service;

import com.example.user_service.externalservice.RatingService;
import com.example.user_service.model.Rating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    private RatingService ratingService;

    @Test
    void createRating(){
        Rating rating=Rating.builder()
                .rating("10")
                .userId("bdaf2333-21a0-48c3-94e5-f65f9f8b84a3")
                .hotelId("36c4cce5-281a-4a95-84bc-b5ee6b4a3579")
                .feedback("This create rating using user just for testing using feign client")
                .build();
       Rating savedRating= ratingService.createRating(rating);
        System.out.println("created");
    }
}
