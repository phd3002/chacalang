package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.repository.OrderRepository;
import com.thungcam.chacalang.repository.ReservationRepository;
import com.thungcam.chacalang.repository.OrderItemRepository;
import com.thungcam.chacalang.service.BranchStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BranchStatisticsServiceImpl implements BranchStatisticsService {

    private final OrderRepository ordersRepository;
    private final ReservationRepository reservationRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Map<String, Object> getBranchStatistics(Long branchId, LocalDateTime fromDate, LocalDateTime toDate) {
        Map<String, Object> data = new HashMap<>();

        // Tổng đơn hàng
        long totalOrders = ordersRepository.countByBranch(branchId, fromDate, toDate);
        System.out.println("Total orders: " + totalOrders + ", From: " + fromDate + ", To: " + toDate + ", Branch ID: " + branchId);
        data.put("totalOrders", totalOrders);

        // Doanh thu
        Double totalRevenue = ordersRepository.sumRevenueByBranch(branchId, fromDate, toDate);
        data.put("totalRevenue", totalRevenue == null ? 0 : totalRevenue);

        long totalReservations = reservationRepository.countByBranch(branchId, fromDate, toDate);
        data.put("totalReservations", totalReservations);

        // Tỷ lệ hủy
        long totalCancelled = ordersRepository.countCancelledByBranch(branchId, fromDate, toDate);
        double cancelRate = totalOrders > 0 ? (double) totalCancelled * 100 / totalOrders : 0;
        data.put("cancelRate", String.format("%.1f", cancelRate));

        // Top món bán chạy nhất
        List<Object[]> topMenus = orderItemRepository.findTopMenusByBranch(branchId, fromDate, toDate);
        String topMenuName = topMenus.isEmpty() ? "Chưa có dữ liệu" : (String) topMenus.get(0)[0];
        data.put("topMenu", topMenuName);

        // Biểu đồ số đơn hàng/ngày trong khoảng filter
        List<Object[]> ordersByDay = ordersRepository.countOrdersByDay(branchId, fromDate, toDate);
        List<String> orderLabels = new ArrayList<>();
        List<Long> orderValues = new ArrayList<>();
        for (Object[] row : ordersByDay) {
            orderLabels.add(row[0].toString()); // ngày dạng String
            orderValues.add((Long) row[1]);
        }
        data.put("ordersLabels", orderLabels);
        data.put("ordersValues", orderValues);

        // Biểu đồ doanh thu/ngày trong khoảng filter
        List<Object[]> revenueByDay = ordersRepository.sumRevenueByDay(branchId, fromDate, toDate);
        List<String> revenueLabels = new ArrayList<>();
        List<Double> revenueValues = new ArrayList<>();
        for (Object[] row : revenueByDay) {
            revenueLabels.add(row[0].toString());
            revenueValues.add(row[1] == null ? 0 : ((BigDecimal) row[1]).doubleValue());
        }
        data.put("revenueLabels", revenueLabels);
        data.put("revenueValues", revenueValues);

        // Top 5 món bán chạy
        List<String> topMenuLabels = new ArrayList<>();
        List<Long> topMenuValues = new ArrayList<>();
        for (int i = 0; i < Math.min(5, topMenus.size()); i++) {
            topMenuLabels.add((String) topMenus.get(i)[0]);
            topMenuValues.add((Long) topMenus.get(i)[1]);
        }
        data.put("topMenuLabels", topMenuLabels);
        data.put("topMenuValues", topMenuValues);

        return data;
    }
}
