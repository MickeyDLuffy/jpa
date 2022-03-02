package com.github.mickeydeelufy.jpa.config;


import com.github.mickeydeelufy.jpa.entity.Customer;
import com.github.mickeydeelufy.jpa.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class InitConfig {
    private final CustomerRepository customerRepository;
    /** Runs when the application is launched, to save some default users in the db **/
    @EventListener(ApplicationReadyEvent.class)
    public void saveCustomersOnLoad() {
        customerRepository.save(new Customer(1L, "Emperor D", "Luffy"));
        customerRepository.save(new Customer(2L, "Rhenee", "Queen"));
        customerRepository.save(new Customer(3L, "Jhey", "Empress"));
    }
}
