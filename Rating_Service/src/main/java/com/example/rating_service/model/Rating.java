package com.example.rating_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_ratings")
@Entity
public class Rating {
    @Id
    private String ratingId;
    private String userId;
    private String hotelId;
    private String rating;
    private String feedback;

}
