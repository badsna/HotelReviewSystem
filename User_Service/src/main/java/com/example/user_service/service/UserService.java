package com.example.user_service.service;

import com.example.user_service.dto.UserRequestDto;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.model.User;

import java.util.List;

public interface UserService {

    User addNewUser(UserRequestDto user);

    List<UserResponseDto> getAllUser();

    UserResponseDto getUserById(String userId);

}
