package com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne //one to one mapping between the user and rider
    @JoinColumn(name = "user_id") // //Forign key column
    private User user;

    private Double rating;
}
