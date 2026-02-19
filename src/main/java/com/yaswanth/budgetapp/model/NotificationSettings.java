package com.yaswanth.budgetapp.model;

import jakarta.persistence.*;
import lombok.*;


@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private Boolean budgetAlertEnabled = true;

    @Builder.Default
    private Boolean loanReminderEnabled = true;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
