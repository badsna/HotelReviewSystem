package com.example.user_service.dto;

import com.example.user_service.model.Rating;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String userId;
    private String userName;
    private String email;
    private String about;
    private List<Rating> ratings=new ArrayList<>();

}
