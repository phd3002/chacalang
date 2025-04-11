package com.thungcam.chacalang.controller;

import com.thungcam.chacalang.entity.OtpToken;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.repository.OtpTokenRepository;
import com.thungcam.chacalang.repository.RoleRepository;
import com.thungcam.chacalang.repository.UserRepository;
import com.thungcam.chacalang.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private MailService mailService;
    @Autowired private OtpTokenRepository otpTokenRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;

    @GetMapping("/dang-ky")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/gui-otp")
    public String sendOtp(@ModelAttribute("user") User user, RedirectAttributes redirect) {
        String otp = String.valueOf((int)(Math.random() * 900000 + 100000));
        OtpToken token = new OtpToken();
        token.setEmail(user.getEmail());
        token.setOtp(otp);
        token.setCreatedAt(LocalDateTime.now());
        otpTokenRepo.deleteByEmail(user.getEmail());
        otpTokenRepo.save(token);
        mailService.sendOtp(user.getEmail(), otp);
        redirect.addFlashAttribute("user", user);
        redirect.addFlashAttribute("emailSent", true);
        return "redirect:/xac-nhan-otp";
    }

    @GetMapping("/xac-nhan-otp")
    public String otpPage() {
        return "verify-otp";
    }

    @PostMapping("/xac-nhan-otp")
    public String verifyOtp(@RequestParam String otp,
                            @ModelAttribute("user") User user,
                            RedirectAttributes redirect) {
        Optional<OtpToken> optional = otpTokenRepo.findByEmailAndOtp(user.getEmail(), otp);
        if (optional.isPresent() && !optional.get().isExpired()) {
            user.setCreatedAt(Instant.from(LocalDateTime.now()));
            user.setRole(roleRepo.findByName("CUSTOMER"));
            userRepo.save(user);
            otpTokenRepo.deleteByEmail(user.getEmail());
            redirect.addFlashAttribute("success", true);
            return "redirect:/dang-nhap";
        } else {
            redirect.addFlashAttribute("error", "Mã OTP không hợp lệ hoặc đã hết hạn!");
            return "redirect:/xac-nhan-otp";
        }
    }
}

