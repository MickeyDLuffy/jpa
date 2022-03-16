package com.github.mickeydeelufy.jpa.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class TestFluentAccesor {

    private String name;
    private String profession;
}
