package com.example.rating_service.service;

import com.example.rating_service.dto.RatingRequestDto;
import com.example.rating_service.dto.RatingResponseDto;
import com.example.rating_service.model.Rating;

import java.util.List;

public interface RatingService {

    Rating addNewRating(RatingRequestDto ratingRequestDto);

    List<RatingResponseDto> getAllRating();

    List<RatingResponseDto> getRatingByUserId(String userId);

    List<RatingResponseDto> getRatingByHotelId(String hotelId);

}
