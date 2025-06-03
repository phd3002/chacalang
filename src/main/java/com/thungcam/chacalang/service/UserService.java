package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.Contact;
import com.thungcam.chacalang.entity.Reservation;
import com.thungcam.chacalang.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public interface UserService extends UserDetailsService {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    void validateUser(User user); // validate logic

    void sendOtp(String email); // tạo OTP + user tạm

    boolean verifyOtp(String email, String otp);

    void registerUser(User user); // lưu user từ OTP

    void deleteOtp(String email);

    User findByEmail(String email); // tìm user từ email

    User findByUsername(String username); // tìm user từ username

    User getAuthenticatedUser(Authentication authentication); // lấy user đã đăng nhập

    void prefillReservationAndContact(User user, Reservation reservation, Contact contact); // điền thông tin vào form đặt bàn

    void sendResetPasswordLink(String email, HttpServletRequest request); // gửi link reset mật khẩu

    boolean isValidResetToken(String token); // kiểm tra token

    void updatePassword(String token, String newPassword); // cập nhật mật khẩu mới

    void updateProfile(Authentication auth, User updatedUser); // cập nhật thông tin người dùng

    void changePassword(Authentication auth, String currentPassword, String newPassword, String confirmPassword);

    List<User> findAllStaff();

    User findById(Long id);

    void saveStaff(User user);

    void deleteStaffById(Long id);
}
