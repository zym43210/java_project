package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "ROLES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID", nullable = false)
    private Long id;
    @Column(name = "ROLE_NAME", nullable = false)
    private String name;
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    @JsonBackReference
    private Collection<User> users;
}
