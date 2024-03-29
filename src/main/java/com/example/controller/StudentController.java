package com.example.controller;

import com.example.entity.Student;
import com.example.services.StudentService;
import io.micronaut.http.*;
import io.micronaut.http.annotation.*;

import java.util.Optional;

@Controller("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Post("/create/{courseId}")
    public Student create(@Body Student student,Long courseId){
        return studentService.createStudentWithCourse(student,courseId);
    }

    @Post("/create/{studentId}/{courseId}")
    public Student update(@Body Student student,Long studentId,Long courseId){
        return studentService.updateStudentWithCourse(student,studentId,courseId);
    }


        @Get("/highest")
        public HttpResponse<Student> getStudentWithHighestAmount() {
        Student studentWithHighestAmount = studentService.getStudentWithHighestAmount();
        if (studentWithHighestAmount != null) {
            return HttpResponse.ok(studentWithHighestAmount);
        } else {
            return HttpResponse.notFound();
        }
    }


        @Get("/find/{id}")
        public Optional<Student> getStudentById(long id){
        return studentService.getStudentById(id);
        }
}