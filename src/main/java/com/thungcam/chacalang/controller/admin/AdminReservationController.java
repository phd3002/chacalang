package com.thungcam.chacalang.controller.admin;

import com.thungcam.chacalang.entity.Reservation;
import com.thungcam.chacalang.enums.ReservationStatus;
import com.thungcam.chacalang.service.BranchService;
import com.thungcam.chacalang.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminReservationController {


    final private ReservationService reservationService;

    final private BranchService branchService;

    @Autowired
    public AdminReservationController(ReservationService reservationService,
                                       BranchService branchService) {
        this.reservationService = reservationService;
        this.branchService = branchService;
    }

    @GetMapping("/reservations")
    public String viewReservations(@RequestParam(value = "branchId", required = false) Long branchId,
                                   Model model) {
        List<Reservation> reservations = (branchId != null)
                ? reservationService.getByBranchId(branchId)
                : reservationService.getAll();

        model.addAttribute("reservations", reservations);
        model.addAttribute("branches", branchService.getAllBranches());
        model.addAttribute("activePage", "reservation");
        return "admin/admin-reservation";
    }

    @PostMapping("/reservations/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam("status") ReservationStatus status,
                               RedirectAttributes redirectAttributes) {
        Reservation reservation = reservationService.getById(id);
        if (reservation != null) {
            reservation.setStatus(status);
            reservationService.save(reservation);
            redirectAttributes.addFlashAttribute("success", "Cập nhật trạng thái thành công!");
        }
        return "redirect:/admin/reservations";
    }

}
