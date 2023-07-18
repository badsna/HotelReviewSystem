package com.example.user_service.service;

import com.example.user_service.dto.UserRequestDto;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.exception.ResourceNotFoundException;
import com.example.user_service.externalservice.HotelService;
import com.example.user_service.externalservice.RatingService;
import com.example.user_service.model.Hotel;
import com.example.user_service.model.Rating;
import com.example.user_service.model.User;
import com.example.user_service.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RestTemplate restTemplate;

  /*  @Autowired
    private DiscoveryClient discoveryClient;*/

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

    @Override
    public User addNewUser(UserRequestDto userRequestDto) {
        //generate unique userId
        String randomUserId = UUID.randomUUID().toString();

        User user = new User();
        user.setUserId(randomUserId);
        user.setUserName(userRequestDto.getUserName());
        user.setEmail(userRequestDto.getEmail());
        user.setAbout(userRequestDto.getAbout());

        return userRepo.save(user);
    }

    @Override
    public List<UserResponseDto> getAllUser() {
        List<User> userList = userRepo.findAll();
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for (User user : userList) {
            UserResponseDto userResponseDto = new UserResponseDto();

            userResponseDto.setUserId(user.getUserId());
            userResponseDto.setUserName(user.getUserName());
            userResponseDto.setEmail(user.getEmail());
            userResponseDto.setAbout(user.getAbout());

            //fetch rating of above user from Rating_Service
            //http://localhost:8083/ratings/users/{{userId}}
            Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);


            List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

            List<Rating> ratingList=ratings.stream().map(rating -> {
                //api call to Hotel_Service to get the hotel
                //http://localhost:8082/hotels/{{hotelId}}
                ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
                Hotel hotel = forEntity.getBody();


                rating.setHotel(hotel);
                return rating;
            }).collect(Collectors.toList());
            userResponseDto.setRatings(ratingList);

            userResponseDtoList.add(userResponseDto);
        }
        return userResponseDtoList;
    }

    @Override
    public UserResponseDto getUserById(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with " + userId + " not found on server !!!!"));
        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setUserId(user.getUserId());
        userResponseDto.setUserName(user.getUserName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setAbout(user.getAbout());

//        String serviceUrl = discoveryClient.getInstances("RATING-SERVICE")
//                .stream()
//                .findFirst()
//                .map(instance -> instance.getUri().toString())
//                .orElseThrow(() -> new IllegalStateException("RATING_SERVICE not found in Eureka"));
//        System.out.println("uri>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+serviceUrl);

        //fetch rating of above user from Rating_Service
        //http://localhost:8083/ratings/users/{{userId}}
//        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/aae536cc-1dec-4a3d-91b2-b4019c22c1bb", Rating[].class);
//        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        //using feignclient
        List<Rating> ratingsOfUser=ratingService.getRatingByUserId(userId);
        List<Rating> ratingList = ratingsOfUser.stream().map(rating -> {

            //api call to Hotel_Service to get the hotel
            //http://localhost:8082/hotels/{{hotelId}}
            /*ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();*/

            //using feignclient
            Hotel hotel= hotelService.getHotelById(rating.getHotelId());

            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        userResponseDto.setRatings(ratingList);
        return userResponseDto;

    }
}
