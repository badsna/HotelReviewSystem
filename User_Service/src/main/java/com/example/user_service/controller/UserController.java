package com.example.user_service.controller;

import com.example.user_service.dto.UserRequestDto;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.model.User;
import com.example.user_service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("add_new_user")
    public ResponseEntity<User> addNewUser(@RequestBody UserRequestDto userRequestDto) {
        User user = userService.addNewUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    //this api is calling another service's api(rating and its calling hotels) so we need to implement circuit breaker

    //name is used to make configuration, fallback-method is to denote which method  to  call when there is fauult
    int retryCount=1;
    @GetMapping("/{userId}")
    //@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallBack")
   // @Retry(name = "ratingHotelService", fallbackMethod ="ratingHotelFallBack" )
    @RateLimiter(name="userRateLimiter", fallbackMethod="ratingHotelFallBack")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String userId) {

        logger .info("retry count: {}",retryCount);
        retryCount++;
        UserResponseDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    //creating fall back method for circuit breaker
    //return time must be  same as the method using @CircuitBreaker

    ResponseEntity<UserResponseDto> ratingHotelFallBack(String userId, Exception ex) {
       // logger.info("Fallback is executed because service is  down: " + ex.getMessage());
        ex.printStackTrace();

        UserResponseDto user = UserResponseDto
                .builder()
                .email("dummy@gmail.com")
                .userName("Dummy")
                .about("This user is created because some service is down")
                .userId("141234")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @GetMapping("get_all_user")
    public ResponseEntity<List<UserResponseDto>> getAllUser() {
        List<UserResponseDto> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }
}
