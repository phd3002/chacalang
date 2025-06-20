package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.Branch;
import com.thungcam.chacalang.repository.BranchRepository;
import com.thungcam.chacalang.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    @Override
    public Branch getBranchById(Long id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found with id: " + id));
    }
}
