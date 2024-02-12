package com.github.mickeydeelufy.jpa.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
public class ErrorMessage<T> {
    private final EmployeeSalary employeeSalary ;
    private int name;

    public static void main(String[] args) {
        var d = new ArrayList<Integer>();
        List<Number> ds = new ArrayList<>();
        d.add(12);
        d.add(15);

        d.add(15);
        d.add(2);
//        d.iterator().forEachRemaining(System.out::println);
//        List<Integer> integers = Collections.unmodifiableList(d);
        Comparator<Object> objectComparator = Collections.reverseOrder();
        System.out.println(d);
        int frequency = Collections.frequency(d, 15);
//        Collections.sort(d);
        System.out.println(frequency);
    }
    public  ErrorMessage(EmployeeSalary salary) {
        this.employeeSalary = salary;
        var v = 10_000_000;


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorMessage that)) return false;

        if (name != that.name) return false;
        return Objects.equals(employeeSalary, that.employeeSalary);
    }

    @Override
    public int hashCode() {
        int result = employeeSalary != null ? employeeSalary.hashCode() : 0;
        result = 31 * result + name;
        return result;
    }
}

