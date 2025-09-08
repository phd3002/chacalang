package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.OrderItem;
import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.repository.OrderItemRepository;
import com.thungcam.chacalang.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    final private JavaMailSender mailSender;

    private final OrderItemRepository orderItemRepository;

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

    @Override
    public void sendOrderConfirmation(Orders order) {
        if (order.getCustomerEmail() == null || order.getCustomerEmail().isBlank()) return;

        String subject = "Xác nhận đơn hàng #" + order.getOrderCode();
        String content = generateOrderContent(order);

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setTo(order.getCustomerEmail());
            helper.setSubject(subject);
            helper.setText(content, false);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Gửi mail thất bại: " + e.getMessage());
        }
    }


    private String generateOrderContent(Orders order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Xin chào ").append(order.getCustomerName()).append(",\n\n");
        sb.append("Mã đơn hàng: ").append(order.getOrderCode()).append("\n");
        sb.append("Chi tiết đơn hàng:\n");

        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
        for (OrderItem item : items) {
            sb.append("- ").append(item.getMenu().getName())
                    .append(" x").append(item.getQuantity())
                    .append(" = ").append(item.getSubtotal()).append(" đ\n");
        }

        sb.append("\nCảm ơn bạn đã ủng hộ!");
        return sb.toString();
    }

    @Override
    public void sendOrderCancellation(Orders order, String reason) {
        if (order.getCustomerEmail() == null || order.getCustomerEmail().isBlank()) return;
        String subject = "Thông báo huỷ đơn hàng #" + order.getOrderCode();

        String content = "Xin chào " + order.getCustomerName() + ",\n\n" +
                "Đơn hàng của bạn với mã #" + order.getOrderCode() + " đã bị huỷ.\n";
        if (reason != null) {
            content += "Lý do huỷ: " + reason + "\n";
        }
        content += "Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi.\n\n" +
                "Số điện thoại: " + order.getBranch().getPhone() + "\n" +
                "Cảm ơn bạn đã ủng hộ!";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(order.getCustomerEmail());
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

}
