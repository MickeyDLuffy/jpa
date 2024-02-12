package com.github.mickeydeelufy.jpa.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mickeydeelufy.jpa.dto.Employee;
import com.github.mickeydeelufy.jpa.dto.TestFluentAccesor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class Subscriber {
    @KafkaListener(topics = "order", groupId = "order")
    public void subscribe(@Payload GenericMessage<Employee> orderResponse) {

        log.info("susbcribing to Order Request Failed: {}", orderResponse);
    }
}

