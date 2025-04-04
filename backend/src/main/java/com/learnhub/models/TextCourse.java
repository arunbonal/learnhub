package com.learnhub.models;

public class TextCourse extends Course {
    public TextCourse(String courseName, String instructor) {
        super(courseName, instructor);
    }

    @Override
    public String getType() {
        return "text";
    }
    
    @Override
    public void displayCourseDetails() {
        System.out.println("Text Course: " + courseName + " by " + instructor);
    }
} 