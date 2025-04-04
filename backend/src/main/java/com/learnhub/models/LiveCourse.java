package com.learnhub.models;

public class LiveCourse extends Course {
    public LiveCourse(String courseName, String instructor) {
        super(courseName, instructor);
    }

    @Override
    public String getType() {
        return "live";
    }
    
    @Override
    public void displayCourseDetails() {
        System.out.println("Live Class: " + courseName + " by " + instructor);
    }
} 