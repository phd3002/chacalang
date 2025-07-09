package com.thungcam.chacalang.controller.staff;

import com.thungcam.chacalang.entity.Invoice;
import com.thungcam.chacalang.entity.PaymentMethod;
import com.thungcam.chacalang.enums.PaymentStatus;
import com.thungcam.chacalang.service.InvoiceService;
import com.thungcam.chacalang.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffInvoiceController {
    private final InvoiceService invoiceService;


    private final PaymentMethodService paymentMethodService;

    @GetMapping("/invoices")
    public String viewInvoices(
            Model model,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "paymentStatus", required = false) PaymentStatus paymentStatus,
            @RequestParam(value = "paymentMethod", required = false) String paymentMethodName,
            @RequestParam("branchId") Long branchId,
            @RequestParam(value = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(value = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        Long paymentMethodId = null;
        if (StringUtils.hasText(paymentMethodName)) {
            PaymentMethod pm = paymentMethodService.findByNameIgnoreCase(paymentMethodName);
            if (pm != null) paymentMethodId = pm.getId();
        }

        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("issuedDate").descending());
        Page<Invoice> invoicePage = invoiceService.searchInvoices(
                branchId,
                search,
                paymentStatus,
                paymentMethodId,
                dateFrom != null ? dateFrom.atStartOfDay() : null,
                dateTo != null ? dateTo.atTime(LocalTime.MAX) : null,
                pageable
        );

        model.addAttribute("invoices", invoicePage.getContent());
        model.addAttribute("totalPages", invoicePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("param", Map.of(
                "search", search == null ? "" : search,
                "paymentStatus", paymentStatus == null ? "" : paymentStatus.name(),
                "paymentMethod", paymentMethodName == null ? "" : paymentMethodName,
                "dateFrom", dateFrom == null ? "" : dateFrom.toString(),
                "dateTo", dateTo == null ? "" : dateTo.toString(),
                "branchId", branchId
        ));
        model.addAttribute("stats", invoiceService.getStats(branchId));
        model.addAttribute("totalRevenue", invoiceService.getTotalRevenue(branchId));
        model.addAttribute("activePage", "invoice");
        model.addAttribute("branchId", branchId);
        return "staff/staff-invoices";
    }

}



