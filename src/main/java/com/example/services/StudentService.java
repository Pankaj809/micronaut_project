package com.example.services;

import com.example.entity.Course;
import com.example.entity.Student;
import com.example.repository.CourseRepository;
import com.example.repository.StudentRepository;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }


    @Transactional
    public Student createStudentWithCourse(Student student, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            student.getCourses().add(course);

            return studentRepository.save(student);
        } else {
            throw new IllegalArgumentException("Course not found with ID: " + courseId);
        }
    }
    public Student updateStudentWithCourse( Student student,long studentId, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Student studentOptional = studentRepository.findById(studentId).orElseThrow(()->new RuntimeException("not found"));



        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            studentOptional.getCourses().add(course);
            return studentRepository.update(studentOptional);
        } else {
            throw new IllegalArgumentException("Course not found with ID: " + courseId);
        }
    }



    public Optional<Student> getStudentById(long studentId){
        return studentRepository.findById(studentId);
    }


    public Student getStudentWithHighestAmount() {
        List<Student> students = studentRepository.findStudentsByHighestAmount();
        return (Student) students;
    }
    }