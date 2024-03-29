package com.example.controller;

import com.example.entity.Course;
import com.example.services.CourseService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.*;
import io.micronaut.http.annotation.*;

import java.net.URI;

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
}
