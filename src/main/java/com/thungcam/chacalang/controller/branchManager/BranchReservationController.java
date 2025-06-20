package com.thungcam.chacalang.controller.branchManager;

import com.thungcam.chacalang.entity.Branch;
import com.thungcam.chacalang.entity.Reservation;
import com.thungcam.chacalang.service.BranchService;
import com.thungcam.chacalang.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/branch-manager")
@RequiredArgsConstructor
public class BranchReservationController {

    private final ReservationService reservationService;

    private final BranchService branchService;

    @GetMapping("/branch-reservation")
    public String showReservationPage(@RequestParam("branchId") Long branchId, Model model) {
        List<Reservation> reservations = reservationService.getReservationsByBranch(branchId);
        Branch branch = branchService.getBranchById(branchId);
        model.addAttribute("reservations", reservations);
        model.addAttribute("branchId", branchId);
        model.addAttribute("branchName", branch.getName());
        return "branch-manager/branch-reservation";
    }

    @PostMapping("/branch-reservation/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status,
                               @RequestParam("branchId") Long branchId) {
        reservationService.updateStatus(id, status);
        return "redirect:/branch-manager/branch-reservation?branchId=" + branchId;
    }

    @PostMapping("/branch-reservation/{id}/delete")
    public String delete(@PathVariable Long id, @RequestParam("branchId") Long branchId) {
        reservationService.delete(id);
        return "redirect:/branch-manager/branch-reservation?branchId=" + branchId;
    }
}
