package com.github.mickeydeelufy.jpa.service;

import com.github.mickeydeelufy.jpa.entity.Customer;
import com.github.mickeydeelufy.jpa.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;
    @InjectMocks
    CustomerService customerService;
    Customer customer;
    @BeforeEach
    void setUp() {
        customer = new Customer("Mickey", "dluffy", "Emperr");

    }

    @Test
    void getCustomers() {

        //when
        when(customerRepository.findAll(Sort.by("firstName").descending()))
                .thenReturn(List.of(customer));

        //then
        List<Customer> customers = customerService.getCustomers();

        //assert
        assertThat(customers, is(List.of(customer)));

    }

    @Test
    void getCustomer() {
        var customerId = 10L;
        //when
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        //then
        Customer customer1 = customerService.getCustomer(customerId);

        //assert
        assertThat(customer1, equalTo(customer));
    }

    @Test
    void getCustomer_throwsResponseStatusException() {
        var customerId = 10L;
        //when
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        //then
        Customer customer1 = customerService.getCustomer(10L);

        //assert
       // assert
        assertThrows(ResponseStatusException.class, () -> customerService.getCustomer(2));
    }

    @Test
    void saveCustomer() {
    }
}