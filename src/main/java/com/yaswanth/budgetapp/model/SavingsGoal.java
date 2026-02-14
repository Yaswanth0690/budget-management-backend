package com.yaswanth.budgetapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavingsGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goalName;

    private Double targetAmount;

    private Double savedAmount = 0.0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
