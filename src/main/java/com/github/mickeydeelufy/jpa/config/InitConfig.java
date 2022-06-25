package com.github.mickeydeelufy.jpa.config;


import com.github.mickeydeelufy.jpa.entity.Customer;
import com.github.mickeydeelufy.jpa.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;
import java.util.Date;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class InitConfig {
    private final CustomerRepository customerRepository;
    /** Runs when the application is launched, to save some default users in the db **/
    @EventListener(ApplicationReadyEvent.class)
    public void saveCustomersOnLoad() {
        customerRepository.save(new Customer(1L, "Emperor D", "Luffy", LocalDateTime.now().plusDays(1L),
                "DLUFFY-202"));
        customerRepository.save(new Customer(2L, "Rhenee", "Queen", LocalDateTime.now().plusDays(2L),"DLUFFY-100" ));
        customerRepository.save(new Customer(3L, "Jhey", "Empress", LocalDateTime.now().plusDays(3L), "DLUFFY-200"));
    }
}
