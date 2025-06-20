package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.BranchMenuStock;
import com.thungcam.chacalang.repository.BranchMenuStockRepository;
import com.thungcam.chacalang.service.BranchMenuStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BranchMenuStockServiceImpl implements BranchMenuStockService {
    private final BranchMenuStockRepository branchMenuStockRepository;

    @Override
    public List<BranchMenuStock> getOutOfStockMenuByBranch(Long branchId) {
        return branchMenuStockRepository.findByBranch_IdAndQuantityLessThanEqual(branchId, 0);
    }

    @Override
    public List<BranchMenuStock> getStockByBranch(Long branchId) {
        return branchMenuStockRepository.findByBranchIdWithMenu(branchId);
    }

    @Override
    public void updateQuantity(Long stockId, int quantity) {
        BranchMenuStock stock = branchMenuStockRepository.findById(stockId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy stock"));
        stock.setQuantity(quantity);
        branchMenuStockRepository.save(stock);
    }
}
