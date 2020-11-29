package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "PLACES")
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"concerts"})
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLACE_ID", nullable = false)
    private Long id;
    @Column(name = "PLACE_NAME", nullable = false)
    private String name;
    @Column(name = "PLACE_COUNTRY", nullable = false)
    private String country;
    @Column(name = "PLACE_CITY", nullable = false)
    private String city;
    @Column(name = "PLACE_STREET", nullable = false)
    private String street;
    @Column(name = "PLACE_NUMBER", nullable = false)
    private Integer number;
    @OneToMany(mappedBy = "place", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonBackReference
    private Collection<Concert> concerts;
}
