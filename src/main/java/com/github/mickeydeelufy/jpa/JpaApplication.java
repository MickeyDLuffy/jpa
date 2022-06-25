package com.github.mickeydeelufy.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/**
 * EnableJpaAuditing for adding createdDate, createdBy (need security) to ur entity. If not added, Auditing returns null
 */
@EnableJpaAuditing
public class JpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }

}
