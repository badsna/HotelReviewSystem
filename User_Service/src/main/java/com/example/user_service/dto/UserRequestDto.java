package com.example.user_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String userId;
    private String userName;
    private String email;
    private String about;


}
