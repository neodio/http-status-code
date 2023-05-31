package com.neodio.httpstatuscode.common.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController implements ErrorController {

    @GetMapping(value = "/error")
    public String error(Model model) {
    
        model.addAttribute("redirectUrl", "/");

        return "common/error";
    }

    @GetMapping(value = "/error/404")
    public String errorDefault() {

        return "common/errorDefault";
    }
}
