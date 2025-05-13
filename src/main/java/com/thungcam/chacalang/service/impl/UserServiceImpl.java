package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.constant.AuthConst;
import com.thungcam.chacalang.entity.*;
import com.thungcam.chacalang.enums.UserStatus;
import com.thungcam.chacalang.exception.BusinessException;
import com.thungcam.chacalang.repository.OtpTokenRepository;
import com.thungcam.chacalang.repository.RoleRepository;
import com.thungcam.chacalang.repository.UserRepository;
import com.thungcam.chacalang.service.MailService;
import com.thungcam.chacalang.service.UserService;
import com.thungcam.chacalang.utils.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepo;
    private final OtpTokenRepository otpTokenRepo;
    private final MailService mailService;

    public UserServiceImpl(UserRepository userRepo,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepo,
                           OtpTokenRepository otpTokenRepo,
                           MailService mailService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
        this.otpTokenRepo = otpTokenRepo;
        this.mailService = mailService;
    }

    @Override
    public void validateUser(User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            user.setUsername(user.getEmail());
        }

        if (!user.getEmail().matches(AuthConst.VALIDATE.REGEX_EMAIL)) {
            throw new BusinessException(AuthConst.ERROR.INVALID_EMAIL_FORMAT);
        }
        if (!user.getPhone().matches(AuthConst.VALIDATE.REGEX_PHONE)) {
            throw new BusinessException(AuthConst.ERROR.INVALID_PHONE_FORMAT);
        }
        if (!user.getPassword().matches(AuthConst.VALIDATE.REGEX_PASSWORD)) {
            throw new BusinessException(AuthConst.ERROR.INVALID_PASSWORD_FORMAT);
        }
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new BusinessException(AuthConst.ERROR.EMAIL_ALREADY_EXISTS);
        }
        if (userRepo.existsByPhone(user.getPhone())) {
            throw new BusinessException(AuthConst.ERROR.PHONE_ALREADY_EXISTS);
        }
    }

    @Override

    public void sendOtp(String email) {
        String otp = String.valueOf((int) (Math.random() * 900000 + 100000));
        otpTokenRepo.deleteByEmail(email);

        OtpToken token = new OtpToken();
        token.setEmail(email);
        token.setOtp(otp);
        token.setCreatedAt(LocalDateTime.now());

        otpTokenRepo.save(token);
        mailService.sendOtp(email, otp);
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        OtpToken token = otpTokenRepo.findByEmail(email);
        return token != null &&
                token.getOtp().equals(otp) &&
                token.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(10));
    }

    @Override
    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        Role role = roleRepo.findById(1);
        user.setRole(role);
        user.setStatus(UserStatus.ACTIVE);
        userRepo.save(user);
    }

    @Override
    public void deleteOtp(String email) {
        otpTokenRepo.deleteByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(input);

        if (user == null) {
            user = userRepo.findByEmail(input);
        }

        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy người dùng.");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().getName());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // vẫn dùng email làm định danh chính
                user.getPassword(),
                List.of(authority)
        );
    }


    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepo.existsByPhone(phone);
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User getAuthenticatedUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            return userRepo.findByEmail(email);
        }
        return null;
    }

    @Override
    public void prefillReservationAndContact(User user, Reservation reservation, Contact contact) {
        if (user == null) {
            return;
        }

        String fullName = user.getFirstName() + " " + user.getLastName();

        if (reservation != null) {
            reservation.setCustomerName(fullName);
            reservation.setEmail(user.getEmail());
            reservation.setPhone(user.getPhone());
        }

        if (contact != null) {
            contact.setName(fullName);
            contact.setEmail(user.getEmail());
            contact.setPhone(user.getPhone());
        }
    }

    @Override
    public void sendResetPasswordLink(String email, HttpServletRequest request) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new BusinessException(AuthConst.ERROR.EMAIL_NOT_EXISTS);
        }
        String token = String.valueOf((int) (Math.random() * 900000 + 100000));
        otpTokenRepo.deleteByEmail(email);

        OtpToken otpToken = new OtpToken();
        otpToken.setEmail(email);
        otpToken.setOtp(token);
        otpToken.setCreatedAt(LocalDateTime.now());
        otpTokenRepo.save(otpToken);

        String baseUrl = UrlUtils.getBaseUrl(request);
        String resetLink = baseUrl + "/auth/reset-password?token=" + token;
        mailService.sendResetPasswordLink(email, resetLink);
    }

    @Override
    public boolean isValidResetToken(String token) {
        OtpToken otpToken = otpTokenRepo.findByOtp(token);
        return otpToken != null && otpToken.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(10));
    }

    @Override
    public void updatePassword(String token, String newPassword) {
        OtpToken otpToken = otpTokenRepo.findByOtp(token);
        if (otpToken == null) {
            throw new BusinessException(AuthConst.ERROR.OTP_NOT_FOUND);
        }

        User user = userRepo.findByEmail(otpToken.getEmail());
        if (user == null) {
            throw new BusinessException(AuthConst.ERROR.EMAIL_NOT_EXISTS);
        }
        if (!newPassword.matches(AuthConst.VALIDATE.REGEX_PASSWORD)) {
            throw new BusinessException(AuthConst.ERROR.INVALID_PASSWORD_FORMAT);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
        otpTokenRepo.deleteByEmail(otpToken.getEmail());
    }

    @Override
    public void updateProfile(Authentication auth, User updatedUser) {
        User user = getAuthenticatedUser(auth);
        if (user == null) {
            throw new BusinessException("Không tìm thấy tài khoản.");
        }

        if (!updatedUser.getPhone().matches(AuthConst.VALIDATE.REGEX_PHONE)) {
            throw new BusinessException(AuthConst.ERROR.INVALID_PHONE_FORMAT);
        }

        // Nếu người dùng đổi số điện thoại và số mới đã tồn tại
        if (!user.getPhone().equals(updatedUser.getPhone())
                && userRepo.existsByPhone(updatedUser.getPhone())) {
            throw new BusinessException(AuthConst.ERROR.PHONE_ALREADY_EXISTS);
        }

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPhone(updatedUser.getPhone());

        userRepo.save(user);
    }

    @Override
    public void changePassword(Authentication auth, String currentPassword, String newPassword, String confirmPassword) {
        User user = getAuthenticatedUser(auth);
        if (user == null) {
            throw new BusinessException(AuthConst.ERROR.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BusinessException(AuthConst.ERROR.CURRENT_PASSWORD_INCORRECT);
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new BusinessException(AuthConst.ERROR.PASSWORD_NOT_MATCH);
        }

        if (!newPassword.matches(AuthConst.VALIDATE.REGEX_PASSWORD)) {
            throw new BusinessException(AuthConst.ERROR.INVALID_PASSWORD_FORMAT);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }
}

