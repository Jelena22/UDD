package com.example.ddmdemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name= "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Column(name="name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

//    @Column(name = "salt", unique = true, nullable = false)
//    private String salt;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;
}
