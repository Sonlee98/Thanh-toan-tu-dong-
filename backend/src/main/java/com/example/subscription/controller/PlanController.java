package com.example.subscription.controller;

import com.example.subscription.entity.Plan;
import com.example.subscription.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanRepository planRepository;

    @GetMapping
    public List<Plan> getActivePlans() {
        return planRepository.findByStatus("ACTIVE");
    }
}
