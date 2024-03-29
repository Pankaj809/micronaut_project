package com.example.controller;

import com.example.entity.Course;
import com.example.services.CourseService;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.*;
import io.micronaut.http.annotation.*;

import java.net.URI;
import java.util.Optional;

@Controller("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Post
    public HttpResponse<Course> createCourse(@Body Course course) {
        Course createdCourse = courseService.createCourse(course);
        return HttpResponse.created(URI.create("/courses/" + createdCourse.getCid())).body(createdCourse);
    }

    @Get("/{id}")
    public @NonNull Optional<Course> findById(Long id){
        return courseService.findById(id);
    }

}
