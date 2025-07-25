package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendOtp(String to, String otp);

    void sendResetPasswordLink(String to, String resetLink);

    void sendOrderConfirmation(Orders order);

    void sendOrderCancellation(Orders order, String reason);
}

