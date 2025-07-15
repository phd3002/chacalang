package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.constant.AuthConst;
import com.thungcam.chacalang.entity.Branch;
import com.thungcam.chacalang.entity.Role;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.enums.UserStatus;
import com.thungcam.chacalang.repository.BranchRepository;
import com.thungcam.chacalang.repository.OrderShipperRepository;
import com.thungcam.chacalang.repository.RoleRepository;
import com.thungcam.chacalang.repository.UserRepository;
import com.thungcam.chacalang.service.BranchStaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BranchStaffServiceImpl implements BranchStaffService {

    private final UserRepository userRepository;

    private final BranchRepository branchRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final OrderShipperRepository orderShipperRepository;

    @Override
    public void updateUserFields(Long id, String firstName, String lastName, String email, String phone) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(AuthConst.ERROR.USER_NOT_FOUND));

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        userRepository.save(user);
    }

    @Override
    public void deleteStaff(Long userId, Long branchId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy nhân viên"));

        if (user.getRole().getName().equalsIgnoreCase("STAFF")) {
            int staffCount = userRepository.countByBranchIdAndRole_Name(branchId, "STAFF");
            if (staffCount <= 1) {
                throw new IllegalStateException("Chi nhánh phải có ít nhất 1 nhân viên phục vụ! Không thể xóa nhân viên cuối cùng.");
            }
        }

        if (user.getRole().getName().equalsIgnoreCase("SHIPPER")) {
            boolean hasActiveOrder = orderShipperRepository.existsActiveOrderByShipperId(userId);
            if (hasActiveOrder) {
                throw new IllegalStateException(AuthConst.ERROR.CANNOT_DELETE_SHIPPER);
            }
        }

        userRepository.delete(user);
    }

    @Override
    public List<User> getStaffByBranch(Long branchId) {
        return userRepository.findByBranchIdAndRoleId(branchId, 3L);
    }

    @Override
    public List<User> getShipperByBranch(Long branchId) {
        return userRepository.findByBranchIdAndRoleId(branchId, 5L);
    }

    @Override
    public void toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(AuthConst.ERROR.USER_NOT_FOUND));

        if (user.getStatus() == UserStatus.ACTIVE) {
            user.setStatus(UserStatus.INACTIVE);
        } else {
            user.setStatus(UserStatus.ACTIVE);
        }
        userRepository.save(user);
    }

    @Override
    public void createStaffOrShipper(Long branchId, String firstName, String lastName, String username, String email, String phone, String password, String role) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new NoSuchElementException(AuthConst.ERROR.BRANCH_NOT_FOUND));

        Role selectedRole;
        if ("STAFF".equalsIgnoreCase(role)) {
            selectedRole = roleRepository.findByName("STAFF");
        } else if ("SHIPPER".equalsIgnoreCase(role)) {
            selectedRole = roleRepository.findByName("SHIPPER");
        } else {
            throw new IllegalArgumentException(AuthConst.ERROR.ERROR);
        }

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(AuthConst.ERROR.USERNAME_ALREADY_EXISTS);
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(passwordEncoder.encode(password));
        user.setStatus(UserStatus.ACTIVE);
        user.setBranch(branch);
        user.setRole(selectedRole);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }
}
