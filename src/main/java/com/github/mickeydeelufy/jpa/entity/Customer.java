package com.github.mickeydeelufy.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
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

/**
 * This @EntityListeners(AuditingEntityListener.class) is neccesary for JPA
 * to provide the @EntityListeners annotation to specify callback listener classes.
 * if not added, createdAt and the rest of the auditing functions return null
 */
@EntityListeners(AuditingEntityListener.class)
public class Customer {

    public Customer(String firstName, String lastName, String nickName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
    }
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    /** @Column is used if the column name differs from the field representing it**/
//     @Column(name = "first_name")
    @NotBlank(message = "First name must not be null or empty")
    private String firstName;
    @NotBlank(message = "Last name must not be null or empty")

    private String lastName;
//    @JsonFormat
//            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @CreatedDate
//    @FutureOrPresent(message = "The date must be today or future")
    private LocalDateTime createdDate;

    /**
     * Your Nicki must be of the pattern "DLUFFY-100"
     */
    @Pattern(regexp  = "^DLUFFY-(\\d){3}$", message = "Nickname must be in the format 'DLUFFY-100'")
    private String nickName;

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
