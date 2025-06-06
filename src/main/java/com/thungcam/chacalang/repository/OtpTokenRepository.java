package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {
    Optional<OtpToken> findByEmailAndOtp(String email, String otp);

    void deleteByEmail(String email);

    OtpToken findByEmail(String email);

    OtpToken findByOtp(String otp);

    void deleteByCreatedAtBefore(LocalDateTime time);
}

