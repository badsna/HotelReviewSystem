package com.example.apigateway_service.model;

import jdk.jfr.StackTrace;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String userId;
    private String accessToken;
    private String refreshToken;
    private long expireAt;
    private Collection<String> authorities;

}
