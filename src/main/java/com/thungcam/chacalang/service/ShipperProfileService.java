package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.User;

public interface ShipperProfileService {
    User findByEmail(String email);

    void save(User user);

    void changePassword(String email, String oldPassword, String newPassword, String confirmPassword);
}
