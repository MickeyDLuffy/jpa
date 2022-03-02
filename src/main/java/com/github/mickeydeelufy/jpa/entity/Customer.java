package com.github.mickeydeelufy.jpa.entity;

import lombok.*;
import org.hibernate.Hibernate;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/***@Entity specifies that this class represents a JPA entity. By default, the class name would be
 * the name of the table i.e customer (if the physical table name is different from the class name,
 * add the @Table() annotation
 * **/
@Entity
/** You can specify the name of the db schema where the table lives**/
//@Table(name = "", schema = "")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
/*** NoArgsConstructor from Lombok generates a constructor with no Arguments i.e Customer() {}
 * This is required by JPA. We do not use the it explicitly
 * **/
//@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    /** @Column is used if the column name differs from the field representing it**/
//     @Column(name = "first_name")
    private String firstName;
    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return id != null && Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
