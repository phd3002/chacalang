package com.thungcam.chacalang.controller;

import com.thungcam.chacalang.constant.AuthConst;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.exception.BusinessException;
import com.thungcam.chacalang.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
//    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
//    private final Map<String, User> temporaryUsers = new HashMap<>(); // Temporary storage for OTP


    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user,
                           HttpSession session,
                           RedirectAttributes redirect,
                           Model model) {
        try {
            userService.validateUser(user);
            session.setAttribute("tempUser", user); // Lưu user tạm
            userService.sendOtp(user.getEmail());
            redirect.addFlashAttribute("email", user.getEmail());
            redirect.addFlashAttribute("successMessage", AuthConst.MESSAGE.REGISTER_SUCCESS);
            return "redirect:/auth/verify-otp";
        } catch (BusinessException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "auth/register";
        }
    }


    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp") String otp,
                            @RequestParam("email") String email,
                            HttpSession session,
                            RedirectAttributes redirect,
                            Model model) {
        if (!userService.verifyOtp(email, otp)) {
            model.addAttribute("errorMessage", AuthConst.ERROR.OTP_INCORRECT_OR_EXPIRED);
            model.addAttribute("email", email);
            return "auth/verify-otp";
        }

        User user = (User) session.getAttribute("tempUser");
        if (user == null || !user.getEmail().equals(email)) {
            throw new IllegalStateException("Không tìm thấy thông tin đăng ký tạm thời.");
        }

        userService.registerUser(user);
        userService.deleteOtp(email);
        session.removeAttribute("tempUser");

        redirect.addFlashAttribute("successMessage", AuthConst.MESSAGE.VERIFY_SUCCESS);
        return "redirect:/auth/verify-otp-success";
    }



    @GetMapping("/verify-otp")
    public String otpPage() {
        return "auth/verify-otp";
    }

    @GetMapping("/verify-otp-success")
    public String otpSuccess() {
        return "auth/verify-otp-success";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email,
                                        RedirectAttributes redirect,
                                        HttpServletRequest request) {
        try {
            userService.sendResetPasswordLink(email, request);
            redirect.addFlashAttribute("successMessage", "Đã gửi link đặt lại mật khẩu qua email.");
        } catch (BusinessException ex) {
            redirect.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/auth/forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetForm(@RequestParam("token") String token, Model model) {
        if (!userService.isValidResetToken(token)) {
            model.addAttribute("errorMessage", "Token không hợp lệ hoặc đã hết hạn.");
            return "auth/reset-password-error";
        }
        model.addAttribute("token", token);
        return "auth/reset-password";
    }

    @PostMapping("/reset-password")
    public String handleReset(@RequestParam("token") String token,
                              @RequestParam("password") String password,
                              RedirectAttributes redirect) {
        try {
            userService.updatePassword(token, password);
            redirect.addFlashAttribute("successMessage", AuthConst.MESSAGE.RESET_PASSWORD_SUCCESS);
            return "redirect:/auth/login";
        } catch (BusinessException ex) {
            redirect.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/auth/reset-password?token=" + token;
        }
    }


}

