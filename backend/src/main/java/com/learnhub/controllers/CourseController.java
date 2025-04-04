package com.learnhub.controllers;

import com.learnhub.models.Course;
import com.learnhub.models.CourseFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        
        // Programming languages
        courses.add(CourseFactory.createCourse("video", "Python for Beginners", "David Wilson"));
        courses.add(CourseFactory.createCourse("video", "Advanced Python Programming", "Sarah Johnson"));
        courses.add(CourseFactory.createCourse("text", "Java Masterclass", "John Smith"));
        courses.add(CourseFactory.createCourse("live", "C++ Programming Fundamentals", "Michael Chen"));
        
        // Web Development
        courses.add(CourseFactory.createCourse("video", "Modern JavaScript", "Emma Davis"));
        courses.add(CourseFactory.createCourse("live", "Full Stack Web Development", "Alex Turner"));
        courses.add(CourseFactory.createCourse("video", "React.js Fundamentals", "Jennifer Lee"));
        courses.add(CourseFactory.createCourse("text", "Node.js Backend Development", "Robert Martinez"));
        
        // Mobile App Development
        courses.add(CourseFactory.createCourse("video", "Android App Development", "Lisa Wang"));
        courses.add(CourseFactory.createCourse("live", "iOS Development with Swift", "Carlos Rodriguez"));
        courses.add(CourseFactory.createCourse("text", "Flutter Cross-Platform Development", "James Wilson"));
        
        // Data Science & AI
        courses.add(CourseFactory.createCourse("video", "Data Science with Python", "Maria Garcia"));
        courses.add(CourseFactory.createCourse("live", "Machine Learning Fundamentals", "Thomas Brown"));
        courses.add(CourseFactory.createCourse("text", "Deep Learning and Neural Networks", "Sophia Taylor"));
        
        return courses;
    }
}