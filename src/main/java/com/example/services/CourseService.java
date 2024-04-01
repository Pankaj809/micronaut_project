package com.example.services;

import com.example.entity.Course;
import com.example.repository.CourseRepository;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class CourseService {
    @Inject
    private CourseRepository courseRepository;

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public @NonNull Optional<Course> findById(long id){
        return courseRepository.findById(id);
    }

   


}
