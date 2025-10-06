package com.example.subscription.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscription")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "plan_code")
    private String planCode;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "status")
    private String status;

    @Column(name = "subscriber")
    private String subscriber;
}
