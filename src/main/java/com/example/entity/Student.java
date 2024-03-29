package com.example.entity;

import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.SerdeImport;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@MappedEntity
@Entity
@Table(name = "student")
@SerdeImport
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    private String sName;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    public Student(String sName) {
        this.sName = sName;
        this.courses = new ArrayList<>();
    }

}