package com.github.mickeydeelufy.jpa.service;

import com.github.mickeydeelufy.jpa.dto.Employee;
import com.github.mickeydeelufy.jpa.dto.TestFluentAccesor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Publisher {
    private final KafkaTemplate<String, Employee> kafkaTemplate;
    public Employee publishOrderEvent(@Payload Employee payload) {
        kafkaTemplate.send("order", payload);
        return payload;
    }
}
