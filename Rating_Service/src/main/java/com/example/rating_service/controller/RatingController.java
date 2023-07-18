package com.example.rating_service.controller;

import com.example.rating_service.dto.RatingRequestDto;
import com.example.rating_service.dto.RatingResponseDto;
import com.example.rating_service.model.Rating;
import com.example.rating_service.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("/add_new_rating")
    public ResponseEntity<Rating> addNewRating(@RequestBody RatingRequestDto ratingRequestDto) {
        Rating rating = ratingService.addNewRating(ratingRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(rating);
    }

    @GetMapping("/get_all_rating")
    public ResponseEntity<List<RatingResponseDto>> getAllRating() {
        List<RatingResponseDto> allRating = ratingService.getAllRating();
        return ResponseEntity.ok(allRating);
    }

    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<RatingResponseDto>> getRatingByUserId(@PathVariable String userId) {
        List<RatingResponseDto> ratingResponseDtoList = ratingService.getRatingByUserId(userId);
        return ResponseEntity.ok(ratingResponseDtoList);
    }
    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<RatingResponseDto>> getRatingByHotelId(@PathVariable String hotelId) {
        List<RatingResponseDto> ratingResponseDtoList = ratingService.getRatingByHotelId(hotelId);
        return ResponseEntity.ok(ratingResponseDtoList);
    }
}
