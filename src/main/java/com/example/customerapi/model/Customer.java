package com.example.customerapi.model;


import ch.qos.logback.core.joran.action.AppenderRefAction;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Setter
@Getter

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Customer(){}
    @PrePersist
    public void beforeInsert(){
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void beforeUpdate(){
        this.updatedAt = LocalDateTime.now();
    }



    public Customer(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
