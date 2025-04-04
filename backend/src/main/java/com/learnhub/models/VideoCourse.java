package com.learnhub.models;

public class VideoCourse extends Course {
    public VideoCourse(String courseName, String instructor) {
        super(courseName, instructor);
    }

    @Override
    public String getType() {
        return "video";
    }
    
    @Override
    public void displayCourseDetails() {
        System.out.println("Video Course: " + courseName + " by " + instructor);
    }
} 