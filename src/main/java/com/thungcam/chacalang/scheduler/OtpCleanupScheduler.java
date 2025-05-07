package com.thungcam.chacalang.scheduler;

import com.thungcam.chacalang.repository.OtpTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OtpCleanupScheduler {
    private final OtpTokenRepository otpTokenRepository;

    public OtpCleanupScheduler(OtpTokenRepository otpTokenRepository) {
        this.otpTokenRepository = otpTokenRepository;
    }

    @Scheduled(fixedRate = 5 * 60 * 1000) // 5 minutes
    public void cleanUpExpiredOtps() {
        LocalDateTime deleteBefore = LocalDateTime.now().minusMinutes(10);
        otpTokenRepository.deleteByCreatedAtBefore(deleteBefore);
    }
}
