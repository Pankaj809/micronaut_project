package com.example.repository;

import com.example.entity.Course;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {



//    @Query("SELECT c FROM Course c WHERE c.cName = :nameOrId OR c.cid = :nameOrId")
//    Course findByNameOrId(String nameOrId);
}










//package com.example.repository;
//import com.example.entity.Course;
//import io.micronaut.data.annotation.Repository;
//import io.micronaut.data.repository.CrudRepository;
//
//@Repository
//public interface CourseRepository extends CrudRepository<Course, Long> {
//
//}
