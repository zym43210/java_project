package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "CONCERTS")
@Getter
@Setter
@ToString(exclude = {"place"})
@EqualsAndHashCode(exclude = {"place", "comments"})
@AllArgsConstructor
@NoArgsConstructor
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONCERT_ID", nullable = false)
    private Long id;
    @Column(name = "CONCERT_NAME", nullable = false)
    private String name;
    @Column(name = "CONCERT_STARTDATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @Column(name = "CONCERT_FINISHDATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finishDate;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "PLACE_ID")
    @JsonManagedReference
    private Place place;
    @OneToMany(mappedBy = "concert", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonBackReference
    private Set<Comment> comments;
}
