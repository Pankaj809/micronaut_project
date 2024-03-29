package com.example.repository;

import com.example.entity.Student;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
//import io.micronaut.data.repository.query.Param;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
            @Query("SELECT s FROM Student s JOIN s.courses c GROUP BY s.sid ORDER BY SUM(c.price) DESC LIMIT 1")
             List<Student> findStudentsByHighestAmount();

}
