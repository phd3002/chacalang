package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.User;

import java.util.List;

public interface BranchStaffService {
    List<User> getStaffByBranch(Long branchId);

    void updateUserFields(Long id, String firstName, String lastName, String email, String phone);

    void deleteStaff(Long userId); // xóa người dùng theo ID

    void toggleUserStatus(Long userId);

    void createStaff(Long branchId, String firstName, String lastName, String username, String email, String phone, String password);
}
