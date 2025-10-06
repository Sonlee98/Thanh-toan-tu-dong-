package com.example.subscription.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "image_payment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImagePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attached_to")
    private String attachedTo;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "pic_byte")
    private byte[] picByte;

    @Column(name = "type")
    private String type;
}
