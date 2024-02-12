package com.github.mickeydeelufy.jpa.controller;

import com.github.mickeydeelufy.jpa.dto.Pageable;
import com.github.mickeydeelufy.jpa.dto.TestFluentAccesor;
import com.github.mickeydeelufy.jpa.entity.Customer;
import com.github.mickeydeelufy.jpa.repository.CustomerRepository;
import com.github.mickeydeelufy.jpa.service.CustomerService;
import com.github.mickeydeelufy.jpa.util.ApiPath;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping(ApiPath.CUSTOMERS)
public class CustomerController {
    public static final int INT = 10;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
        /** **
         * U can use JpaSort.unsafe(property) when sorting by a property that doesnt exist directly on the entity
         */
//       return customerRepository.findAll(Sort.by("firstName").descending()); // using Sort.by
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable long id) {

        return customerService.getCustomer(id);
    }

    @GetMapping("/test")
    public String testingCOnro() {
        return "tested";
    }

    /**
     * Validating a pathvariable with @Pattern and @Validated on the restcontroller
     * We added a handler in the controller advice too
     *
     * @param nickname
     * @return
     */
    @GetMapping("/{nickname}/nicki")
    public ResponseEntity<Customer> getCustomerByNickname(@Pattern(regexp = "^DLUFFY-(\\d){3}$",
            message = "Nickname must be in the format 'DLUFFY-100'") @PathVariable String nickname) {
        return ResponseEntity.ok(customerRepository.getCustomerByNickName(nickname));
    }

    @GetMapping("/p")
    public Page<Customer> findCustomersPaged() {
        TestFluentAccesor testFluentAccesor = new TestFluentAccesor()
                .name("Micke")
                .profession("lol");
        System.out.println(testFluentAccesor.name());
        List.of(Sort.Order.by("firstname"), Sort.Order.by("firstname"));
        return customerRepository.findAllPaged(PageRequest.of(5, 3));
    }

    @GetMapping("/slice")
    public Slice<Customer> findAllPagedUsingSlice(Pageable pageable) {
        return customerRepository.findAllPagedUsingSlice(PageRequest.of(pageable.getPage(), pageable.getSize()));
    }

    @GetMapping("/native-query")
    public Page<Customer> findAllPagedWithNativeQuery(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return customerRepository.findAllPagedWithNativeQuery(PageRequest.of(page, size));
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
    public int updateNameWith(@PathVariable String id) {
        return customerRepository.updateNameWith(id);
    }

    @PostMapping
    public ResponseEntity<Customer> saveUser(@RequestBody @Valid Customer customer) {
        var newCustomer = customerService.saveCustomer(customer);
        return ResponseEntity.created(URI.create(ApiPath.CUSTOMERS + "/" + newCustomer.getId()))
                .body(newCustomer);
    }
}
