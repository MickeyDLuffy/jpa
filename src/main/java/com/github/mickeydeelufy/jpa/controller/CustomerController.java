package com.github.mickeydeelufy.jpa.controller;

import com.github.mickeydeelufy.jpa.dto.Pageable;
import com.github.mickeydeelufy.jpa.dto.TestFluentAccesor;
import com.github.mickeydeelufy.jpa.entity.Customer;
import com.github.mickeydeelufy.jpa.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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
        return customerRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer " + id + " not found"));
    }

    @GetMapping("/p")
    public Page<Customer> findCustomersPaged() {
        TestFluentAccesor testFluentAccesor = new TestFluentAccesor()
                .name("Micke")
                        .profession("lol");
        System.out.println(testFluentAccesor.name());
        List.of(Sort.Order.by("firstname"),Sort.Order.by("firstname") );
        return customerRepository.findAllPaged(PageRequest.of(0,1));
    }

    @GetMapping("/slice")
    public Slice<Customer> findAllPagedUsingSlice(Pageable pageable) {
        return customerRepository.findAllPagedUsingSlice(PageRequest.of(pageable.getPage(), pageable.getSize()));
    }

    @GetMapping("/native-query")
    public Page<Customer> findAllPagedWithNativeQuery(@RequestParam(defaultValue = "0")  int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        return customerRepository.findAllPagedWithNativeQuery(PageRequest.of(page,size));
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
    public ResponseEntity<Customer> saveUser(@RequestBody @Valid Customer customer) {
        return ResponseEntity.ok(this.customerRepository.save(customer));
    }
}
