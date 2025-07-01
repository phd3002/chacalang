package com.thungcam.chacalang.controller.staff;

import com.thungcam.chacalang.entity.Contact;
import com.thungcam.chacalang.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffContactController {

    private final ContactService contactService;

    // Hiển thị danh sách liên hệ (lọc theo chi nhánh nếu có)
    @GetMapping("/contacts")
    public String listContacts(@RequestParam(value = "branchId") Long branchId, Model model) {
        List<Contact> contacts = contactService.getContactsByBranch(branchId);
        model.addAttribute("contacts", contacts);
        model.addAttribute("branchId", branchId);
        model.addAttribute("activePage", "contacts");
        return "staff/staff-contacts";
    }
}
