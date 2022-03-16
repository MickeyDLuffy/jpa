package com.github.mickeydeelufy.jpa.dto;

import lombok.Data;

import java.util.List;

@Data
public class Pageable {
    private int page = 0;
    private int size = 20;
    private List<String> sort = List.of();
}
