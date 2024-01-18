package com.example.finshot.domain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 3, nullable = false)
    private String employeeNumber;

    @Column(nullable = false)
    private String name;

    private String phoneNumber;

    private String position;

    private String email;
}
