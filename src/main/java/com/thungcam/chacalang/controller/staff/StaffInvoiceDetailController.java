package com.thungcam.chacalang.controller.staff;

import com.thungcam.chacalang.entity.Invoice;
import com.thungcam.chacalang.enums.PaymentStatus;
import com.thungcam.chacalang.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffInvoiceDetailController {

    private final InvoiceService invoiceService;

    @GetMapping("/invoices/{id}/detail")
    public String invoiceDetail(@PathVariable("id") Long id, @RequestParam Long branchId, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        model.addAttribute("invoice", invoice);
        model.addAttribute("paymentStatus", invoice.getPaymentStatus());
        System.out.println("Payment Status: " + invoice.getPaymentStatus().name());
        model.addAttribute("activePage", "invoices");
        model.addAttribute("branchId", branchId);
        // Các attribute bổ sung nếu cần
        return "staff/staff-invoice-detail"; // Trả về đúng tên view .html (không cần .html trong return)
    }

    @PostMapping("/invoices/{id}/payment-status")
    @ResponseBody
    public ResponseEntity<?> updatePaymentStatus(
            @PathVariable("id") Long invoiceId,
            @RequestParam("status") PaymentStatus status
    ) {
        try {
            invoiceService.updatePaymentStatus(invoiceId, status);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", ex.getMessage()));
        }
    }

}


