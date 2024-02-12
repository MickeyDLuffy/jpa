package com.github.mickeydeelufy.jpa.controller;

import com.github.mickeydeelufy.jpa.dto.Employee;
import com.github.mickeydeelufy.jpa.dto.TestFluentAccesor;
import com.github.mickeydeelufy.jpa.service.Publisher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class TestController {
  private final Publisher publisher;

    public TestController(Publisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping("/lol")
    public void d() {
        Employee mickey = new Employee(10, "Mickey");
        publisher.publishOrderEvent(mickey) ;
    }

    @GetMapping("/hash")
    public Map<String, String> f() {
        return Map.of("nam", "la", "lo", "78", "mim", "lov");
    }
}
