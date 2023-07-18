package com.example.rating_service.service;

import com.example.rating_service.dto.RatingRequestDto;
import com.example.rating_service.dto.RatingResponseDto;
import com.example.rating_service.model.Rating;
import com.example.rating_service.repo.RatingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepo ratingRepo;

    @Override
    public Rating addNewRating(RatingRequestDto ratingRequestDto) {
        //generate unique userId
        String randomUserId = UUID.randomUUID().toString();

        Rating rating = new Rating();

        rating.setRatingId(randomUserId);
        rating.setUserId(ratingRequestDto.getUserId());
        rating.setHotelId(ratingRequestDto.getHotelId());
        rating.setRating(ratingRequestDto.getRating());
        rating.setFeedback(ratingRequestDto.getFeedback());

        return ratingRepo.save(rating);
    }

    @Override
    public List<RatingResponseDto> getAllRating() {

        List<Rating> ratingList = ratingRepo.findAll();

        List<RatingResponseDto> ratingResponseDtoList = new ArrayList<>();
        for (Rating rating : ratingList) {
            RatingResponseDto ratingResponseDto = new RatingResponseDto();

            ratingResponseDto.setRatingId(rating.getRatingId());
            ratingResponseDto.setUserId(rating.getUserId());
            ratingResponseDto.setHotelId(rating.getHotelId());
            ratingResponseDto.setRating(rating.getRating());
            ratingResponseDto.setFeedback(rating.getFeedback());

            ratingResponseDtoList.add(ratingResponseDto);

        }
        return ratingResponseDtoList;
    }

    @Override
    public List<RatingResponseDto> getRatingByUserId(String userId) {
        List<Rating> ratingList = ratingRepo.findByUserId(userId);

        List<RatingResponseDto> ratingResponseDtoList = new ArrayList<>();
        for (Rating rating : ratingList) {
            RatingResponseDto ratingResponseDto = new RatingResponseDto();

            ratingResponseDto.setRatingId(rating.getRatingId());
            ratingResponseDto.setUserId(rating.getUserId());
            ratingResponseDto.setHotelId(rating.getHotelId());
            ratingResponseDto.setRating(rating.getRating());
            ratingResponseDto.setFeedback(rating.getFeedback());

            ratingResponseDtoList.add(ratingResponseDto);

        }

        return ratingResponseDtoList;
    }

    @Override
    public List<RatingResponseDto> getRatingByHotelId(String hotelId) {
        List<Rating> ratingList = ratingRepo.findByHotelId(hotelId);

        List<RatingResponseDto> ratingResponseDtoList = new ArrayList<>();
        for (Rating rating : ratingList) {
            RatingResponseDto ratingResponseDto = new RatingResponseDto();

            ratingResponseDto.setRatingId(rating.getRatingId());
            ratingResponseDto.setUserId(rating.getUserId());
            ratingResponseDto.setHotelId(rating.getHotelId());
            ratingResponseDto.setRating(rating.getRating());
            ratingResponseDto.setFeedback(rating.getFeedback());

            ratingResponseDtoList.add(ratingResponseDto);

        }
        return ratingResponseDtoList;
    }

}
