package com.yaswanth.budgetapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.YearMonth;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private String month; // "2026-02"

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
