package com.learnhub.services;

import com.learnhub.models.Course;
import com.learnhub.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        
        // Initialize with some courses if none exist
        logger.info("Checking for existing courses...");
        long courseCount = courseRepository.count();
        logger.info("Found {} courses in the database", courseCount);
        
        if (courseCount == 0) {
            logger.info("No courses found. Initializing sample courses...");
            initializeSampleCourses();
        } else {
            logger.info("Courses already exist in the database. Skipping initialization.");
        }
        
        // Add a test course for debugging
        addTestCourse();
    }

    public List<Course> getAllCourses() {
        logger.info("Getting all courses");
        List<Course> courses = courseRepository.findAll();
        logger.info("Found {} courses", courses.size());
        return courses;
    }

    public Optional<Course> getCourseById(String id) {
        logger.info("Getting course by ID: {}", id);
        return courseRepository.findById(id);
    }

    public List<Course> getCoursesByCategory(String category) {
        logger.info("Getting courses by category: {}", category);
        List<Course> courses = courseRepository.findByCategory(category);
        logger.info("Found {} courses in category {}", courses.size(), category);
        return courses;
    }

    public List<Course> getPopularCourses() {
        logger.info("Getting popular courses");
        return courseRepository.findTop8ByOrderByEnrolledStudentsDesc();
    }

    public List<Course> getTopRatedCourses() {
        logger.info("Getting top rated courses");
        return courseRepository.findTop8ByOrderByRatingDesc();
    }

    public Course saveCourse(Course course) {
        logger.info("Saving course: {}", course.getTitle());
        return courseRepository.save(course);
    }

    public void deleteCourse(String id) {
        logger.info("Deleting course with ID: {}", id);
        courseRepository.deleteById(id);
    }
    
    private void addTestCourse() {
        logger.info("Adding test course for debugging");
        Course testCourse = new Course(
            "Test Course - Please Ignore",
            "This is a test course for debugging purposes",
            "System Admin",
            "https://images.unsplash.com/photo-1517694712202-14dd9538aa97",
            "Test",
            1,
            0.0,
            Arrays.asList("Test Topic 1", "Test Topic 2"),
            "Beginner"
        );
        testCourse.setEnrolledStudents(100);
        testCourse.setRating(5.0);
        
        courseRepository.save(testCourse);
        logger.info("Test course added successfully");
    }

    private void initializeSampleCourses() {
        logger.info("Creating sample courses...");
        List<Course> sampleCourses = Arrays.asList(
            new Course(
                "Introduction to Python Programming",
                "Learn Python programming from scratch. Perfect for beginners who want to master one of the most popular programming languages.",
                "Dr. John Smith",
                "https://images.unsplash.com/photo-1515879218367-8466d910aaa4?q=80&w=2069&auto=format&fit=crop",
                "Programming",
                24,
                49.99,
                Arrays.asList("Python Basics", "Data Structures", "Functions", "OOP in Python", "File Handling"),
                "Beginner"
            ),
            new Course(
                "Advanced Java Development",
                "Take your Java skills to the next level with advanced topics like multithreading, design patterns, and microservices.",
                "Sarah Johnson",
                "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?q=80&w=2070&auto=format&fit=crop",
                "Programming",
                36,
                79.99,
                Arrays.asList("Multithreading", "Design Patterns", "Spring Boot", "Microservices", "JVM Optimization"),
                "Advanced"
            ),
            new Course(
                "Full Stack Web Development",
                "Master both frontend and backend development with this comprehensive course covering MERN stack.",
                "Michael Chen",
                "https://images.unsplash.com/photo-1547658719-da2b51169166?q=80&w=2064&auto=format&fit=crop",
                "Web Development",
                48,
                99.99,
                Arrays.asList("HTML/CSS", "JavaScript", "React", "Node.js", "MongoDB", "Express"),
                "Intermediate"
            ),
            new Course(
                "Machine Learning Fundamentals",
                "Learn the core concepts of machine learning including supervised and unsupervised learning, neural networks, and more.",
                "Dr. Emily Williams",
                "https://images.unsplash.com/photo-1620712943543-bcc4688e7485?q=80&w=2065&auto=format&fit=crop",
                "Data Science",
                30,
                89.99,
                Arrays.asList("Supervised Learning", "Unsupervised Learning", "Neural Networks", "Feature Engineering", "Model Evaluation"),
                "Intermediate"
            ),
            new Course(
                "Cybersecurity Essentials",
                "Learn how to protect systems and networks from digital attacks with this comprehensive cybersecurity course.",
                "Robert Garcia",
                "https://images.unsplash.com/photo-1560807707-8cc77767d783?q=80&w=2070&auto=format&fit=crop",
                "Security",
                32,
                69.99,
                Arrays.asList("Network Security", "Encryption", "Ethical Hacking", "Security Policies", "Incident Response"),
                "Intermediate"
            ),
            new Course(
                "Cloud Computing with AWS",
                "Master cloud infrastructure and services using Amazon Web Services (AWS), the leading cloud platform.",
                "Jennifer Lee",
                "https://images.unsplash.com/photo-1614064548237-096d0baa7b17?q=80&w=2070&auto=format&fit=crop",
                "Cloud Computing",
                28,
                79.99,
                Arrays.asList("EC2", "S3", "Lambda", "CloudFormation", "AWS Security"),
                "Intermediate"
            ),
            new Course(
                "Mobile App Development with Flutter",
                "Build beautiful native apps for iOS and Android with Flutter and Dart.",
                "Alex Turner",
                "https://images.unsplash.com/photo-1551650975-87deedd944c3?q=80&w=1974&auto=format&fit=crop",
                "Programming",
                40,
                89.99,
                Arrays.asList("Dart Basics", "Flutter Widgets", "State Management", "API Integration", "App Publishing"),
                "Intermediate"
            ),
            new Course(
                "DevOps Engineering",
                "Learn modern DevOps practices, tools, and methodologies for efficient software delivery.",
                "David Miller",
                "https://images.unsplash.com/photo-1618335829737-2228915674e0?q=80&w=2070&auto=format&fit=crop",
                "Cloud Computing",
                45,
                94.99,
                Arrays.asList("CI/CD", "Docker", "Kubernetes", "Jenkins", "Infrastructure as Code"),
                "Advanced"
            ),
            new Course(
                "Data Analytics with Python",
                "Master data analysis using Python, Pandas, NumPy, and visualization libraries.",
                "Lisa Anderson",
                "https://images.unsplash.com/photo-1551288049-bebda4e38f71?q=80&w=2070&auto=format&fit=crop",
                "Data Science",
                35,
                74.99,
                Arrays.asList("Pandas", "NumPy", "Data Visualization", "Statistical Analysis", "Data Cleaning"),
                "Intermediate"
            ),
            new Course(
                "UI/UX Design Fundamentals",
                "Learn the principles of user interface and user experience design.",
                "Maria Rodriguez",
                "https://images.unsplash.com/photo-1586717791821-3f44a563fa4c?q=80&w=2070&auto=format&fit=crop",
                "Web Development",
                25,
                59.99,
                Arrays.asList("Design Principles", "Wireframing", "Prototyping", "User Research", "Design Systems"),
                "Beginner"
            )
        );
        
        // Set additional fields for better display
        for (Course course : sampleCourses) {
            course.setEnrolledStudents((int) (Math.random() * 500) + 100);
            course.setRating(3.5 + Math.random() * 1.5);
        }
        
        courseRepository.saveAll(sampleCourses);
        logger.info("Saved {} sample courses", sampleCourses.size());
    }
} 