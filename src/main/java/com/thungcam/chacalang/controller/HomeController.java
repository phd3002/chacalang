package com.thungcam.chacalang.controller;

import com.thungcam.chacalang.entity.Branch;
import com.thungcam.chacalang.entity.Category;
import com.thungcam.chacalang.entity.Menu;
//import com.thungcam.chacalang.entity.Product;
import com.thungcam.chacalang.entity.Reservation;
import com.thungcam.chacalang.enums.ReservationStatus;
import com.thungcam.chacalang.service.BranchService;
import com.thungcam.chacalang.service.CategoryService;
import com.thungcam.chacalang.service.MenuService;
import com.thungcam.chacalang.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BranchService branchService;

    @Autowired
    ReservationService reservationService;

    @ModelAttribute("branches")
    public List<Branch> getBranches() {
        return branchService.getAllBranches();
    }


    @GetMapping("/")
    public String showHomePage(Model model) {
        List<Menu> products = menuService.getAllMenu();
        List<Category> categories = categoryService.getAllCategories();
        List<Branch> branches = branchService.getAllBranches();

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("branches", branches);
        model.addAttribute("reservation", new Reservation()); // 👈 thêm form object

        return "index";
    }

    @PostMapping("/dat-ban")
    public String submitReservation(@ModelAttribute("reservation") Reservation reservation,
                                    RedirectAttributes redirectAttributes) {
        reservation.setStatus(ReservationStatus.PENDING);
//        reservation.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        reservationService.save(reservation);
        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("reservation", new Reservation()); // 👈 thêm lại object
        return "redirect:/#reservation";
    }


}
