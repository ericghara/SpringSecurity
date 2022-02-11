package org.ericghara.controller;

import lombok.AllArgsConstructor;
import org.ericghara.service.CustomerService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.authorization.client.util.Http;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@AllArgsConstructor
@Controller
public class WebController {

    private final CustomerService customerService;
    private final APIController apiController;

    @GetMapping(path = "/")
    public String index() {
        return "external";
    }

    @GetMapping(path = "/customers")
    public String customers(Authentication authentication, Model model) {
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("username", authentication.getName());
        return "customers";
    }

    @PostMapping(path="/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        apiController.logout(request, response);
        if ( response.getStatus() == HttpServletResponse.SC_BAD_REQUEST) {
            return "redirect:/bad_request.html";
        }
        return "redirect:/";
    }

}
