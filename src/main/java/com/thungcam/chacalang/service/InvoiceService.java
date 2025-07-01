package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.Invoice;
import com.thungcam.chacalang.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public interface InvoiceService {
    Page<Invoice> searchInvoices(Long branchId, String search, PaymentStatus paymentStatus, Long paymentMethodId, LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable);

    Invoice getInvoiceById(Long id);

    void updatePaymentStatus(Long invoiceId, PaymentStatus status);

    Map<String, Long> getStats(Long branchId);

    BigDecimal getTotalRevenue(Long branchId);
}


