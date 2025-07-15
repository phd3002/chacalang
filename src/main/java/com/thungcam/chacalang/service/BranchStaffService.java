package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.User;

import java.util.List;

public interface BranchStaffService {
    List<User> getStaffByBranch(Long branchId);

    List<User> getShipperByBranch(Long branchId);

    void updateUserFields(Long id, String firstName, String lastName, String email, String phone);

    void deleteStaff(Long userId, Long branchId);

    void toggleUserStatus(Long userId);

    void createStaffOrShipper(Long branchId, String firstName, String lastName, String username, String email, String phone, String password, String role);
}
