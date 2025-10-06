package com.example.subscription.repository;

import com.example.subscription.entity.ImagePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagePaymentRepository extends JpaRepository<ImagePayment, Long> {
    List<ImagePayment> findByAttachedTo(String attachedTo);
}
