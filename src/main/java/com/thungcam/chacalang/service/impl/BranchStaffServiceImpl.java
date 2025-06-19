package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.Branch;
import com.thungcam.chacalang.entity.Role;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.enums.UserStatus;
import com.thungcam.chacalang.repository.BranchRepository;
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

    @Override
    public void updateUserFields(Long id, String firstName, String lastName, String email, String phone) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy user"));

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        userRepository.save(user);
    }

    @Override
    public void deleteStaff(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> getStaffByBranch(Long branchId) {
        return userRepository.findByBranchIdAndRoleId(branchId, 3L); // giả sử role STAFF = 3
    }

    @Override
    public void toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng"));

        if (user.getStatus() == UserStatus.ACTIVE) {
            user.setStatus(UserStatus.INACTIVE);
        } else {
            user.setStatus(UserStatus.ACTIVE);
        }
        userRepository.save(user);
    }

    @Override
    public void createStaff(Long branchId, String firstName, String lastName, String username, String email, String phone, String password) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy chi nhánh"));
        Role staffRole = roleRepository.findByName("STAFF");

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(passwordEncoder.encode(password));
        user.setStatus(UserStatus.ACTIVE);
        user.setBranch(branch);
        user.setRole(staffRole);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

}
