package it.samu3l.redirect.controller;

import it.samu3l.redirect.model.Alias;
import it.samu3l.redirect.service.RedirectService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@Controller
public class RedirectController {

    @Autowired
    private RedirectService redirectService;

    @GetMapping("/**")
    public void handleRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String host = request.getServerName(); // Extract domain from the request
        String[] parts = host.split("\\."); //Take the subdomain (first part of the domain)
        if (parts.length < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid domain");
        }
        String subdomain = parts[0];
        System.out.println(subdomain);

        // Search for the alias in the database
        Optional<Alias> a = redirectService.getAlias(subdomain);
        if (a.isPresent()) {
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.sendRedirect(a.get().getDestination());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alias not found");
        }
    }
}