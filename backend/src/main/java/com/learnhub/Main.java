package com.learnhub;

import com.learnhub.models.Course;
import com.learnhub.models.CourseFactory;

public class Main {
    public static void main(String[] args) {
        // Creating different types of courses using Factory
        Course course1 = CourseFactory.createCourse("video", "Java Basics", "John Doe");
        Course course2 = CourseFactory.createCourse("text", "Python Fundamentals", "Jane Smith");
        Course course3 = CourseFactory.createCourse("live", "Web Development Bootcamp", "Alice Brown");

        // Display course details
        course1.displayCourseDetails();
        course2.displayCourseDetails();
        course3.displayCourseDetails();
    }
} 