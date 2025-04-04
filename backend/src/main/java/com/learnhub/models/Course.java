package com.learnhub.models;

public abstract class Course {
    protected String courseName;
    protected String instructor;

    public Course(String courseName, String instructor) {
        this.courseName = courseName;
        this.instructor = instructor;
    }
    
    // Getters for JSON serialization
    public String getCourseName() {
        return courseName;
    }
    
    public String getInstructor() {
        return instructor;
    }
    
    // Abstract method to get course type
    public abstract String getType();

    public abstract void displayCourseDetails();
}