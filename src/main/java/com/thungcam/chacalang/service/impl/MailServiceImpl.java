package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendOtp(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Xác nhận đăng ký - Chả Cá Thung Cấm");
        message.setText("Mã OTP xác nhận đăng ký của bạn là: " + otp + "\nHiệu lực trong 5 phút.");
        mailSender.send(message);
    }

    public void sendResetPasswordLink(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Đặt lại mật khẩu - Chả Cá Thung Cấm");
        message.setText("Bạn vừa yêu cầu đặt lại mật khẩu. Vui lòng nhấn vào link sau để đặt mật khẩu mới:\n" + resetLink + "\nLink có hiệu lực trong 10 phút.");
        mailSender.send(message);
    }

}
