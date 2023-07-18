package com.example.user_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "micro_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    private String userId;

    @Column(length = 20)
    private String userName;

    private String email;
    private String about;

    //this column is not to be stored in this microservice db
    @Transient
    private List<Rating> ratings=new ArrayList<>();
}
