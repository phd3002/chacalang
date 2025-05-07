package com.thungcam.chacalang.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(
        name = "otp_tokens",
        schema = "thungcam_db",
        indexes = {
                @Index(name = "idx_otp_created_at", columnList = "createdAt")
        }
)

public class OtpToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "otp")
    private String otp;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "temp_data", columnDefinition = "TEXT")
    private String tempData;


    public boolean isExpired() {
        return createdAt.plusMinutes(5).isBefore(LocalDateTime.now());

    }

}