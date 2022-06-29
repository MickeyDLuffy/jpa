package com.github.mickeydeelufy.jpa.service;

import com.github.mickeydeelufy.jpa.entity.Customer;
import com.github.mickeydeelufy.jpa.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getCustomers() {
        return customerRepository.findAll(Sort.by("firstName").descending()); // using Sort.by

    }

    public Customer getCustomer( long id) {
        return customerRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer " +id + " not found"));
    }


    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
