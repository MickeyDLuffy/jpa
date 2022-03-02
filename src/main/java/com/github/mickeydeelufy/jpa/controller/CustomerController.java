package com.github.mickeydeelufy.jpa.controller;

import com.github.mickeydeelufy.jpa.entity.Customer;
import com.github.mickeydeelufy.jpa.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getCustomers() {
       return customerRepository.findAll(Sort.by("firstName").descending()); // using Sort.by
        /** **
         * U can use JpaSort.unsafe(property) when sorting by a property that doesnt exist directly on the entity
         */
//       return customerRepository.findAll(Sort.by("firstName").descending()); // using Sort.by
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable long id) {
        return customerRepository.findById(id);
    }

    @GetMapping("/p")
    public List<Customer> findCustomersPaged(Pageable pageable) {
        return customerRepository.findAllPaged(pageable).getContent();
    }

    @GetMapping("/firstname")
    public Customer getCustomerByFirstName() {
        return customerRepository.getCustomerByFirstName("Emperor D");
    }

    @GetMapping("/names")
    public List<Customer> getAllCustomersWhereNameInList() {
        return customerRepository.getAllCustomersWhereNameInList(List.of("Rhenee", "Jhey"));
    }
    @PutMapping("/{id}")
    public int updateNameWith(@PathVariable String id ) {
        return customerRepository.updateNameWith("jack");
    }

    @PostMapping
    public void saveUser() {
//         customerRepository.insertIntoTable();
    }
}
