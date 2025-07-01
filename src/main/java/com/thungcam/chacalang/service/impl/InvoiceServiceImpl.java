package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.Invoice;
import com.thungcam.chacalang.enums.PaymentStatus;
import com.thungcam.chacalang.enums.ShippingMethod;
import com.thungcam.chacalang.repository.InvoiceRepository;
import com.thungcam.chacalang.repository.PaymentMethodRepository;
import com.thungcam.chacalang.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;

    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public Page<Invoice> searchInvoices(Long branchId, String search, PaymentStatus paymentStatus, Long paymentMethodId, LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable) {
        return invoiceRepository.searchInvoices(
                branchId,
                StringUtils.hasText(search) ? search : null,
                paymentStatus,
                paymentMethodId,
                dateFrom,
                dateTo,
                pageable
        );
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));
    }

    @Override
    public void updatePaymentStatus(Long invoiceId, PaymentStatus status) {
        Invoice invoice = getInvoiceById(invoiceId);
        invoice.setPaymentStatus(status);
        invoice.setIssuedDate(LocalDateTime.now());
        invoiceRepository.save(invoice);
    }

    @Override
    public Map<String, Long> getStats(Long branchId) {
        Map<String, Long> result = new HashMap<>();
        for (PaymentStatus ps : PaymentStatus.values()) {
            long count = invoiceRepository.count((root, query, cb) -> cb.and(
                    cb.equal(root.get("order").get("branch").get("id"), branchId),
                    cb.equal(root.get("order").get("shippingMethod"), ShippingMethod.PICKUP),
                    cb.equal(root.get("paymentStatus"), ps)
            ));
            result.put(ps.name().toLowerCase(), count);
        }
        return result;
    }

    @Override
    public BigDecimal getTotalRevenue(Long branchId) {
        List<Invoice> invoices = invoiceRepository.findAll((root, query, cb) -> cb.and(
                cb.equal(root.get("order").get("branch").get("id"), branchId),
                cb.equal(root.get("order").get("shippingMethod"), ShippingMethod.PICKUP),
                cb.equal(root.get("paymentStatus"), PaymentStatus.PAID)
        ));
        return invoices.stream().map(Invoice::getFinalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}


