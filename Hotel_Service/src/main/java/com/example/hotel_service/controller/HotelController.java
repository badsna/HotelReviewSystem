package com.example.hotel_service.controller;

import com.example.hotel_service.dto.HotelRequestDto;
import com.example.hotel_service.dto.HotelResponseDto;
import com.example.hotel_service.model.Hotel;
import com.example.hotel_service.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PreAuthorize("hasAuthority('Admin')")

    @PostMapping("add_new_hotel")
    public ResponseEntity<Hotel> addNewUser(@RequestBody HotelRequestDto hotelRequestDto) {
        Hotel user = hotelService.addNewHotel(hotelRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PreAuthorize("hasAuthority('SCOPE_internal')")
    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelResponseDto> getHotelById(@PathVariable String hotelId) {
        HotelResponseDto hotel = hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotel);
    }

    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    @GetMapping("get_all_hotel")
    public ResponseEntity<List<HotelResponseDto>> getAllHotel() {
        List<HotelResponseDto> allHotel = hotelService.getAllHotel();
        return ResponseEntity.ok(allHotel);
    }
}
