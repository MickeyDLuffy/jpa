package com.github.mickeydeelufy.jpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mickeydeelufy.jpa.entity.Customer;
import com.github.mickeydeelufy.jpa.repository.CustomerRepository;
import com.github.mickeydeelufy.jpa.service.CustomerService;
import com.github.mickeydeelufy.jpa.util.ApiPath;
import com.github.mickeydeelufy.jpa.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerService customerService;

    @Autowired
    ObjectMapper objectMapper;

    List<Customer> data;

    @BeforeEach
    void setUp() {
        data = List.of(new Customer(10L, "Mickey", "D",
                LocalDateTime.now().plusDays(1), "DLUFFY-130"));
    }



    @Test
    void getCustomerShouldReturnListOfCustomers() throws Exception {
        //when

        when(customerService.getCustomers())
                .thenReturn(data);
        //then

        var mock = List.of();

        MvcResult mvcResult = mvc.perform(
                        get(ApiPath.CUSTOMERS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(objectMapper.writeValueAsString(data));

        verify(customerService, times(1)).getCustomers();


    }


    @Test
    void getCustomerShouldReturnOnlyOneCustomerHavingId() throws Exception {
        when(customerService.getCustomer(10L)).thenReturn(data.get(0));
        String mvcResult = mvc.perform(get(ApiPath.CUSTOMERS + "/{id}" ,10)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(10)))
                .andReturn()
                .getResponse()
                .getContentAsString();


        assertThat(mvcResult)
                .isEqualTo(objectMapper.writeValueAsString(data.get(0)));
    }

    @Test
    void customerWasNotFoundShouldThrowException() throws Exception {
        when(customerService.getCustomer(2L)).thenThrow(ResponseStatusException.class);
        this.mvc.perform(get(ApiPath.CUSTOMERS + "/{id}" ,2))
                .andExpect(status().isNotFound());

        verify(customerService, times(1)).getCustomer(2L);
    }

    @Test
    void saveUserHasInvalidRequestBodyAndReturnsBadRequest() throws Exception {
        var customer = new Customer("", "Emperor", "DLUFFY-100");

        when(customerService.saveCustomer(customer)).thenReturn(customer);
        mvc.perform(post(ApiPath.CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.stringify(customer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors.firstName").value("First name must not be null or empty"))
                .andExpect(jsonPath("$.errors.firstName").isNotEmpty());

    }
    @Test
    void saveUserSavesUserAndReturnsSavedUserWith201() throws Exception {
        var customer = data.get(0);
        when(customerService.saveCustomer(customer)).thenReturn(customer);
        String mvcResult = this.mvc.perform(post(ApiPath.CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.stringify(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", ApiPath.CUSTOMERS + "/" + customer.getId()))
                .andExpect(jsonPath("$.firstName").value("Mickey"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(customerService, times(1)).saveCustomer(customer);
        assertThat(mvcResult)
                .isEqualTo(TestUtil.stringify(customer));

        ;
    }
}