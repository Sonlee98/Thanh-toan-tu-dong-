package com.example.subscription.controller;

import com.example.subscription.dto.PaymentImageDto;
import com.example.subscription.entity.ImagePayment;
import com.example.subscription.repository.ImagePaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentImageController {

    private final ImagePaymentRepository imagePaymentRepository;

    @GetMapping("/images")
    public List<PaymentImageDto> getPaymentImages(@RequestParam("attached_to") String attachedTo) {
        List<ImagePayment> images = imagePaymentRepository.findByAttachedTo(attachedTo);
        return images.stream().map(img -> PaymentImageDto.builder()
                .id(img.getId())
                .name(img.getName())
                .contentType(img.getType())
                .base64("data:" + img.getType() + ";base64," + Base64Utils.encodeToString(img.getPicByte()))
                .build())
            .collect(Collectors.toList());
    }
}
