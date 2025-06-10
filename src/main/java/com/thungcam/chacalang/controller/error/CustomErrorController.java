package com.thungcam.chacalang.controller.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error/404")
    public String notFound() {
        return "error/404"; // trả về templates/error/404.html
    }
}


