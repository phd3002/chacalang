package com.thungcam.chacalang.controller.shipper;

import com.thungcam.chacalang.entity.OrderShipper;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.service.ShipperOrderHistoryService;
import com.thungcam.chacalang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Controller
@RequestMapping("/shipper")
@RequiredArgsConstructor
public class ShipperOrderHistoryController {

    private final ShipperOrderHistoryService shipperOrderHistoryService;
    private final UserService userService;

    @GetMapping("/orders-history")
    public String ordersHistory(
            @RequestParam("branchId") Long branchId,
            @RequestParam(value = "month", required = false) String month,
            @RequestParam(value = "fromDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(value = "toDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            Authentication authentication,
            Model model
    ) {
        User shipper = userService.findByEmail(authentication.getName());

        LocalDateTime fromDateTime = null;
        LocalDateTime toDateTime = null;
        if (month != null && !month.isEmpty()) {
            YearMonth yearMonth = YearMonth.parse(month);
            fromDateTime = yearMonth.atDay(1).atStartOfDay();
            toDateTime = yearMonth.atEndOfMonth().atTime(23,59,59);
        } else {
            if (fromDate != null) fromDateTime = fromDate.atStartOfDay();
            if (toDate != null) toDateTime = toDate.atTime(23,59,59);
        }

        List<OrderShipper> historyList = shipperOrderHistoryService.getOrderHistory(
                shipper.getId(),
                fromDateTime,
                toDateTime
        );

        int totalOrders = historyList.size();
        long totalAmount = historyList.stream()
                .map(os -> os.getOrder().getInvoice())
                .filter(invoice -> invoice != null && invoice.getFinalAmount() != null)
                .mapToLong(invoice -> invoice.getFinalAmount().longValue())
                .sum();

        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("assignedOrders", historyList);
        model.addAttribute("branchId", branchId);
        model.addAttribute("month", month);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("activePage", "orders-history");
        return "shipper/shipper-orders-history";
    }
}

