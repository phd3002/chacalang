package com.thungcam.chacalang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtp(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Xác nhận đăng ký - Chả Cá Thung Cấm");
        message.setText("Mã OTP xác nhận đăng ký của bạn là: " + otp + "\nHiệu lực trong 5 phút.");
        mailSender.send(message);
    }
}

