package com.yaswanth.budgetapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean budgetAlertEnabled = true;

    private Boolean loanReminderEnabled = true;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
