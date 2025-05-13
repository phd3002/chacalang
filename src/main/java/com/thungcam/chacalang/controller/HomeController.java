package com.thungcam.chacalang.controller;

import com.thungcam.chacalang.entity.*;
//import com.thungcam.chacalang.entity.Product;
import com.thungcam.chacalang.enums.ReservationStatus;
import com.thungcam.chacalang.service.*;
import com.thungcam.chacalang.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {

    final private MenuService menuService;

    final private CategoryService categoryService;

    final private BranchService branchService;

    final private ReservationService reservationService;

    final private ContactService contactService;

    final private UserService userService;

    @Autowired
    public HomeController(MenuService menuService,
                          CategoryService categoryService,
                          BranchService branchService,
                          ReservationService reservationService,
                          ContactService contactService,
                          UserService userService) {
        this.menuService = menuService;
        this.categoryService = categoryService;
        this.branchService = branchService;
        this.reservationService = reservationService;
        this.contactService = contactService;
        this.userService = userService;
    }

    @ModelAttribute("branches")
    public List<Branch> getBranches() {
        return branchService.getAllBranches();
    }


    @GetMapping({"/", "/homepage"})
    public String showHomePage(Model model, Authentication authentication) {
        Reservation reservation = new Reservation();
        Contact contact = new Contact();

        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.getAuthenticatedUser(authentication);
            userService.prefillReservationAndContact(user, reservation, contact);
            if (user != null) {
                String fullName = user.getFirstName() + " " + user.getLastName();
                model.addAttribute("fullName", fullName); // vẫn dùng cho header
            }
        }

        model.addAttribute("reservation", reservation); // GÁN reservation có sẵn dữ liệu

        model.addAttribute("products", menuService.getAllMenus());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("branches", branchService.getAllBranches());
        model.addAttribute("contact", contact);

        return "index";
    }


    @PostMapping("/dat-ban")
    public String submitReservation(@ModelAttribute("reservation") Reservation reservation,
                                    RedirectAttributes redirectAttributes) {
        reservationService.save(reservation);
        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("reservation", new Reservation()); // 👈 thêm lại object
        return "redirect:/#reservation";
    }

    @PostMapping("/lien-he")
    public String submitContact(@ModelAttribute("contact") Contact contact,
                                RedirectAttributes redirectAttributes) {
        contactService.save(contact);
        redirectAttributes.addFlashAttribute("contactSuccess", true);
        return "redirect:/#contact";
    }
}
