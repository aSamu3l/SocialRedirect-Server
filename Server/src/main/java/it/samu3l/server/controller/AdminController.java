package it.samu3l.server.controller;

import it.samu3l.server.service.AliasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AliasService aliasService;

    @Value("${my.domain}")
    private String domain;

    @RequestMapping(value = "/")
    public RedirectView redirectToAdmin() {
        return new RedirectView("/admin");
    }

    @RequestMapping("")
    public String admin(Model model) {
        model.addAttribute("aliases", aliasService.getAllAliases());
        model.addAttribute("mydomain", domain);

        return "admin";
    }
}