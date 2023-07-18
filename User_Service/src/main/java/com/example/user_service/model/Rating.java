package com.example.user_service.model;

import lombok.*;

//this will  be called from other microservice
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {
    private String ratingId;
    private String userId;
    private String hotelId;
    private String rating;
    private String feedback;
    private Hotel hotel;
}
