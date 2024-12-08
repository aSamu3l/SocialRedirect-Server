package it.samu3l.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/login")
public class LoginController {
    @RequestMapping(value = "/")
    public RedirectView redirectToLogin() {
        return new RedirectView("/login");
    }

    @RequestMapping("")
    public String login() {
        return "login";
    }
}
