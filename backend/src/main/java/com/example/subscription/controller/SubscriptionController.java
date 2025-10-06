package com.example.subscription.controller;

import com.example.subscription.dto.UpgradeRequest;
import com.example.subscription.entity.Plan;
import com.example.subscription.entity.Subscription;
import com.example.subscription.repository.PlanRepository;
import com.example.subscription.repository.SubscriptionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    @GetMapping("/current")
    public ResponseEntity<?> getCurrent(@RequestParam("subscriber") String subscriber) {
        Optional<Subscription> latest = subscriptionRepository.findTopBySubscriberOrderByStartDateDesc(subscriber);
        return latest.<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(Map.of()));
    }

    @PostMapping("/upgrade")
    public ResponseEntity<?> upgrade(@Valid @RequestBody UpgradeRequest request) {
        Plan plan = planRepository.findByCode(request.getPlanCode())
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime expiration = start.plus(plan.getDuration(), ChronoUnit.MONTHS);

        Subscription sub = subscriptionRepository.findTopBySubscriberOrderByStartDateDesc(request.getSubscriber())
                .orElse(Subscription.builder().subscriber(request.getSubscriber()).build());

        sub.setPlanCode(plan.getCode());
        sub.setPrice(plan.getPrice() != null ? plan.getPrice() : BigDecimal.ZERO);
        sub.setStartDate(start);
        sub.setExpirationDate(expiration);
        sub.setStatus("ACTIVE");

        subscriptionRepository.save(sub);
        return ResponseEntity.ok(Map.of("message", "Subscription upgraded", "expirationDate", expiration));
    }
}
