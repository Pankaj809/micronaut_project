package com.example.services;

import com.example.entity.Course;
import com.example.repository.CourseRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

@Singleton
public class CourseService {
    @Inject
    private CourseRepository courseRepository;

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }


}
