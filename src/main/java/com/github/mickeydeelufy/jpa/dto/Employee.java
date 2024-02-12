package com.github.mickeydeelufy.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class Employee implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int age;
    private String name;
}
