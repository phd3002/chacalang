package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.Role;
import com.thungcam.chacalang.repository.RoleRepository;
import com.thungcam.chacalang.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
