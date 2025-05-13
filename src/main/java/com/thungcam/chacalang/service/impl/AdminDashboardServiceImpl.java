package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.repository.InvoiceRepository;
import com.thungcam.chacalang.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public AdminDashboardServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public long getTotalOrders() {
        return invoiceRepository.countInvoices();
    }

    @Override
    public BigDecimal getTotalRevenue() {
        BigDecimal total = invoiceRepository.sumTotalRevenue();
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public long getNewCustomers() {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        return invoiceRepository.countNewCustomersThisMonth(startOfMonth);
    }

    @Override
    public Map<String, BigDecimal> getRevenueChartData() {
        LocalDateTime from = LocalDate.now().minusDays(7).atStartOfDay();
        List<Object[]> rows = invoiceRepository.getDailyRevenue(from);
        return rows.stream().collect(Collectors.toMap(
                r -> r[0].toString(),
                r -> (BigDecimal) r[1],
                (a, b) -> b,
                LinkedHashMap::new
        ));
    }
}
