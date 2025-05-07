package com.thungcam.chacalang.controller.admin;

import com.thungcam.chacalang.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminContactController {
    final private ContactService contactService;

    @Autowired
    public AdminContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contacts")
    public String listContact(Model model) {
        model.addAttribute("activePage", "contact");
        model.addAttribute("contacts", contactService.getAllContacts());
        return "admin/contact-list";
    }
}
