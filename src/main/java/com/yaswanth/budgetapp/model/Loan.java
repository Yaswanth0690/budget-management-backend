package com.yaswanth.budgetapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalAmount;

    private Double remainingAmount;

    private Double interestRate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
