package com.learnhub.models;

public class CourseFactory {
    public static Course createCourse(String type, String courseName, String instructor) {
        if (type.equalsIgnoreCase("video")) {
            return new VideoCourse(courseName, instructor);
        } else if (type.equalsIgnoreCase("text")) {
            return new TextCourse(courseName, instructor);
        } else if (type.equalsIgnoreCase("live")) {
            return new LiveCourse(courseName, instructor);
        } else {
            throw new IllegalArgumentException("Invalid course type: " + type);
        }
    }
} 