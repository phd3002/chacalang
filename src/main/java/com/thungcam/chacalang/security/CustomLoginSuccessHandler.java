package com.thungcam.chacalang.security;

import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("==> Người dùng '" + username + "' đăng nhập với quyền:");
        authorities.forEach(auth -> System.out.println(" - " + auth.getAuthority()));
        User user = userRepository.findByEmail(username);
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            switch (role) {
                case "ROLE_ADMIN" -> {
                    response.sendRedirect("/admin/dashboard");
                    return;
                }
                case "ROLE_BRANCH_MANAGER" -> {

                    System.out.println("==> Tìm kiếm người dùng '" + username + "' trong cơ sở dữ liệu...");
                    System.out.println("Chi nhánh của người dùng '" + username + "': " + user.getBranch());
                    if (user.getBranch() == null) {
                        response.sendRedirect("/error?reason=unauthorized");
                        return;
                    }
                    Long branchId = user.getBranch().getId();
                    System.out.println("==> Người dùng '" + username + "' có chi nhánh ID: " + branchId);
                    response.sendRedirect("/branch-manager/branch-dashboard?branchId=" + branchId);
                    return;
                }
                case "ROLE_STAFF" -> {
                    System.out.println("==> Tìm kiếm người dùng '" + username + "' trong cơ sở dữ liệu...");
                    System.out.println("Chi nhánh của người dùng '" + username + "': " + user.getBranch());
                    if (user.getBranch() == null) {
                        response.sendRedirect("/error?reason=unauthorized");
                        return;
                    }
                    Long branchId = user.getBranch().getId();
                    System.out.println("==> Người dùng '" + username + "' có chi nhánh ID: " + branchId);
                    response.sendRedirect("/staff/dashboard?branchId=" + branchId);
                    return;
                }
                case "ROLE_SHIPPER" -> {
                    System.out.println("==> Tìm kiếm người dùng '" + username + "' trong cơ sở dữ liệu...");
                    System.out.println("Chi nhánh của người dùng '" + username + "': " + user.getBranch());
                    if (user.getBranch() == null) {
                        response.sendRedirect("/error?reason=unauthorized");
                        return;
                    }
                    Long branchId = user.getBranch().getId();
                    System.out.println("==> Người dùng '" + username + "' có chi nhánh ID: " + branchId);
                    response.sendRedirect("/shipper/dashboard?branchId=" + branchId);
                    return;
                }
            }
        }
        response.sendRedirect("/");
    }
}
