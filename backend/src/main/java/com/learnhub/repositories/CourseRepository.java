package com.learnhub.repositories;

import com.learnhub.models.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    List<Course> findByCategory(String category);
    List<Course> findByDifficulty(String difficulty);
    List<Course> findTop8ByOrderByEnrolledStudentsDesc();
    List<Course> findTop8ByOrderByRatingDesc();
} 