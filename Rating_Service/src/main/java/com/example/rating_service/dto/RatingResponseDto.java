package com.example.rating_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingResponseDto {
    private String ratingId;
    private String userId;
    private String hotelId;
    private String rating;
    private String feedback;

}
