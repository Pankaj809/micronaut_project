package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Data;
import jakarta.persistence.*;
import java.util.List;
@Entity
@Table(name = "courses")
@Data
@SerdeImport
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    private String cName;
    private double price;

    @ManyToMany( fetch = FetchType.EAGER, mappedBy = "courses", cascade = {CascadeType.MERGE})
    @JsonIgnore
    private List<Student> students;

}
