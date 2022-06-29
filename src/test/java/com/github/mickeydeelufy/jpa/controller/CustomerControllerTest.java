package com.github.mickeydeelufy.jpa.controller;

import static org.assertj.core.api.Assertions.*;

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
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
//         var c = new Customer()
        when(customerService.getCustomer(10L)).thenReturn(data.get(0));
        MvcResult mvcResult = mvc.perform(get(ApiPath.CUSTOMERS + "/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(10)))
                .andReturn();


        assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(objectMapper.writeValueAsString(data.get(0)));
    }

    @Test
    void customerWasNotFoundShouldThrowException() throws Exception {
        when(customerService.getCustomer(2L)).thenThrow(ResponseStatusException.class);
        this.mvc.perform(get(ApiPath.CUSTOMERS + "/2"))
                .andExpect(status().isNotFound());

        verify(customerService, times(1)).getCustomer(2L);
    }

    @Test
    void saveUserSavesUserAndReturnsSavedUserWith201() throws Exception {
        when(customerService.saveCustomer(data.get(0))).thenReturn(data.get(0));

        MvcResult mvcResult = this.mvc.perform(post(ApiPath.CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(data.get(0))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Dluffy")))
                .andReturn();

        verify(customerService, times(1)).saveCustomer(data.get(0));
        assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(String.valueOf(data.get(0)))

        ;
    }
}