package com.psa.SpringbootPsa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
        name = "employee",
        indexes = {
                @Index(name = "idx_email", columnList = "email")
        }
)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;


    @Column(name = "mobile", unique = true, nullable = false)
    private String mobile;

}
