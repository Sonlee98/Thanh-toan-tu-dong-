package com.example.subscription.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpgradeRequest {
    @NotBlank
    private String planCode;

    @NotBlank
    private String subscriber;
}