package com.example.subscription.repository;

import com.example.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findTopBySubscriberOrderByStartDateDesc(String subscriber);
}
