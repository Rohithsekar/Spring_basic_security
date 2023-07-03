package com.example.Spring_basic_security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="user")
public class User {
    @Id
    private int id;
    private String name;
    private String profession;
    private int age;
}
