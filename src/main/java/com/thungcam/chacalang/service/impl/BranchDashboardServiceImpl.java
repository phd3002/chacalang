package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.dto.BranchDashboardDTO;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.repository.InvoiceRepository;
import com.thungcam.chacalang.repository.OrderRepository;
import com.thungcam.chacalang.repository.ReservationRepository;
import com.thungcam.chacalang.service.BranchDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BranchDashboardServiceImpl implements BranchDashboardService {
    private final OrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public BranchDashboardDTO getDashboardData(Long branchId) {
        Long totalOrders = orderRepository.countByBranch_Id(branchId);
        Long pending = orderRepository.countByBranch_IdAndStatus(branchId, OrderStatus.PENDING);
        Long completed = orderRepository.countByBranch_IdAndStatus(branchId, OrderStatus.COMPLETED);
        Long cancelled = orderRepository.countByBranch_IdAndStatus(branchId, OrderStatus.CANCELLED);
        Long confirmed = orderRepository.countByBranch_IdAndStatus(branchId, OrderStatus.CONFIRMED);
        Long preparing = orderRepository.countByBranch_IdAndStatus(branchId, OrderStatus.PREPARING);
        Long shipping = orderRepository.countByBranch_IdAndStatus(branchId, OrderStatus.SHIPPING);
        Long refunded = orderRepository.countByBranch_IdAndStatus(branchId, OrderStatus.REFUNDED);

        BigDecimal revenue = invoiceRepository.sumFinalAmountByBranch(branchId);
        Long todayReservations = reservationRepository.countByBranchIdAndDate(branchId, LocalDate.now());

        return new BranchDashboardDTO(totalOrders, revenue, todayReservations, pending, completed, cancelled, confirmed, preparing, shipping, refunded);
    }
}
