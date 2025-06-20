package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.Branch;
import java.util.List;

public interface BranchService {
    List<Branch> getAllBranches();

    Branch getBranchById(Long id);
}
