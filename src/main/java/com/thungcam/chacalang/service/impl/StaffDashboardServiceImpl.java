package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.dto.StaffDashboardDTO;
import com.thungcam.chacalang.dto.StaffDashboardDTO.*;
import com.thungcam.chacalang.entity.*;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.enums.PaymentStatus;
import com.thungcam.chacalang.repository.*;
import com.thungcam.chacalang.service.StaffDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class StaffDashboardServiceImpl implements StaffDashboardService {

    @Autowired
    private OrderRepository ordersRepository;
    @Autowired
    private BranchMenuStockRepository stockRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public StaffDashboardDTO getDashboardData(Long branchId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        long ordersNew = ordersRepository.countByStatusAndBranchId(OrderStatus.PENDING, branchId);
        long ordersPreparing = ordersRepository.countByStatusAndBranchId(OrderStatus.PREPARING, branchId);
        long ordersShipping = ordersRepository.countByStatusAndBranchId(OrderStatus.SHIPPING, branchId);
        long ordersCompleted = ordersRepository.countByStatusAndBranchId(OrderStatus.COMPLETED, branchId);
        long ordersCancelled = ordersRepository.countByStatusAndBranchId(OrderStatus.CANCELLED, branchId);

        BigDecimal todayRevenue = invoiceRepository.sumFinalAmountByStatusAndBranchAndDate(
                PaymentStatus.PAID, branchId, startOfDay, endOfDay
        );
        if (todayRevenue == null) todayRevenue = BigDecimal.ZERO;

        // Tồn kho thấp
        List<BranchMenuStock> lowStocks = stockRepository.findLowStockByBranch(branchId, 5);
        List<StockAlertItem> stockAlerts = lowStocks.stream()
                .map(s -> new StockAlertItem(s.getMenu().getName(), s.getQuantity()))
                .toList();

        // Đặt bàn hôm nay
        List<Reservation> reservations = reservationRepository.findTodayReservation(branchId, today);
        List<ReservationTodayItem> todayReservations = reservations.stream()
                .map(r -> new ReservationTodayItem(
                        r.getCustomerName(),
                        r.getReservationTime(),
                        r.getNumberOfPeople(),
                        r.getStatus() != null ? r.getStatus().toString() : ""))
                .toList();

        // Thông báo (nếu bạn có repo notification thì lấy động, demo cứng)
        List<NotificationItem> notifications = List.of(
                new NotificationItem("Ca tối đổi lịch", "Nhớ đến sớm chuẩn bị!", "warning", LocalDateTime.now()),
                new NotificationItem("Kiểm tra kho cuối ngày", "Tồn kho cá thấp.", "urgent", LocalDateTime.now())
        );

        return new StaffDashboardDTO(
                ordersNew,
                ordersPreparing,
                ordersShipping,
                ordersCompleted,
                ordersCancelled,
                todayRevenue,
                stockAlerts,
                todayReservations,
                notifications
        );
    }
}

