package org.ericghara.controller;

import lombok.AllArgsConstructor;
import org.ericghara.entity.Customer;
import org.ericghara.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value="/api")
@AllArgsConstructor
public class APIController {

    CustomerService customerService;

    @PostMapping("/addcustomer")
    public void addCustomer(@RequestBody Customer customer, HttpServletResponse response) {
        customerService.addCustomer(customer);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.logout();
        } catch (Exception e) {
            System.out.println(e.getMessage() );
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
    }
}
