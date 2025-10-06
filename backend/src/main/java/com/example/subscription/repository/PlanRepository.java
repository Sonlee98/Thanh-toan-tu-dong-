package com.example.subscription.repository;

import com.example.subscription.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByStatus(String status);
    Optional<Plan> findByCode(String code);
}
