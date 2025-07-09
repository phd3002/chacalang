package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.constant.AuthConst;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.exception.BusinessException;
import com.thungcam.chacalang.repository.UserRepository;
import com.thungcam.chacalang.service.ShipperProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipperProfileServiceImpl implements ShipperProfileService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword, String confirmPassword) {
        User user = findByEmail(email);
        if (!passwordEncoder.matches(oldPassword, user.getPassword()))
            throw new BusinessException(AuthConst.ERROR.CURRENT_PASSWORD_INCORRECT);
        if (!newPassword.equals(confirmPassword))
            throw new RuntimeException(AuthConst.ERROR.PASSWORD_NOT_MATCH);
        if (!newPassword.matches(AuthConst.VALIDATE.REGEX_PASSWORD))
            throw new RuntimeException(AuthConst.ERROR.INVALID_PASSWORD_FORMAT);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
