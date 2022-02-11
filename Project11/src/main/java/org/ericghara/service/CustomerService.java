package org.ericghara.service;

import lombok.AllArgsConstructor;
import org.ericghara.entity.Customer;
import org.ericghara.repositories.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> findAll() {
        List<Customer> customers = new LinkedList<>();
        customerRepository.findAll().forEach(customers::add);
        return customers;
    }

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

}
