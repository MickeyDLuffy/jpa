package com.github.mickeydeelufy.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/**
 * EnableJpaAuditing for adding createdDate, createdBy (need security) to ur entity. If not added, Auditing returns null
 * May cause @WebMvcTest to throw errror 'nested exception is java.lang.IllegalArgumentException: JPA metamodel must not be empty!'
 * By using @EnableJpaRepositories you are explicitly telling Spring Boot's auto-configuration to back off
 * and that you'll handle Spring Data yourself. I think what's happening is that @WebMvcTest is turning off JPA (i.e. not finding your @Entity classes)
 * but @EnableJpaRepositories is still active so it complains that it can't find any JPA models.
 * You can create a separate config file and annotate it with it
 */
//@EnableJpaAuditing
public class JpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }

}
