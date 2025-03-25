import { Course } from '../types/course';

// Use the correct backend URL - check if it matches your backend configuration
const API_URL = 'http://localhost:8080/api';

// Sample fallback courses for development/testing
const sampleCourses: Course[] = [
  {
    id: 'sample1',
    title: 'Introduction to Python Programming',
    description: 'Learn Python programming from scratch. Perfect for beginners who want to master one of the most popular programming languages.',
    instructor: 'Dr. John Smith',
    imageUrl: 'https://images.unsplash.com/photo-1526379879527-8559ecfcaec0?q=80&w=2074&auto=format&fit=crop',
    category: 'Programming',
    durationHours: 24,
    price: 49.99,
    topics: ['Python Basics', 'Data Structures', 'Functions', 'OOP in Python', 'File Handling'],
    difficulty: 'Beginner',
    enrolledStudents: 230,
    rating: 4.7
  },
  {
    id: 'sample2',
    title: 'Advanced Java Development',
    description: 'Take your Java skills to the next level with advanced topics like multithreading, design patterns, and microservices.',
    instructor: 'Sarah Johnson',
    imageUrl: 'https://images.unsplash.com/photo-1517694712202-14dd9538aa97?q=80&w=2070&auto=format&fit=crop',
    category: 'Programming',
    durationHours: 36,
    price: 79.99,
    topics: ['Multithreading', 'Design Patterns', 'Spring Boot', 'Microservices', 'JVM Optimization'],
    difficulty: 'Advanced',
    enrolledStudents: 180,
    rating: 4.5
  },
  {
    id: 'sample3',
    title: 'Machine Learning Fundamentals',
    description: 'Learn the core concepts of machine learning including supervised and unsupervised learning, neural networks, and more.',
    instructor: 'Dr. Emily Williams',
    imageUrl: 'https://images.unsplash.com/photo-1620712943543-bcc4688e7485?q=80&w=2065&auto=format&fit=crop',
    category: 'Data Science',
    durationHours: 30,
    price: 89.99,
    topics: ['Supervised Learning', 'Unsupervised Learning', 'Neural Networks', 'Feature Engineering', 'Model Evaluation'],
    difficulty: 'Intermediate',
    enrolledStudents: 210,
    rating: 4.9
  }
];

export const courseService = {
  getAllCourses: async (): Promise<Course[]> => {
    console.log('Fetching all courses from:', `${API_URL}/courses`);
    try {
      const response = await fetch(`${API_URL}/courses`);
      console.log('Response status:', response.status);
      
      if (!response.ok) {
        throw new Error(`Failed to fetch courses: ${response.status} ${response.statusText}`);
      }
      
      const data = await response.json();
      console.log('Received courses data:', data.length > 0 ? `${data.length} courses` : 'No courses');
      
      // Return sample courses if the API returns empty array
      if (Array.isArray(data) && data.length === 0) {
        console.warn('API returned empty array, using sample courses instead');
        return sampleCourses;
      }
      
      return data;
    } catch (error) {
      console.error('Error fetching courses:', error);
      console.warn('Using sample courses as fallback');
      return sampleCourses;
    }
  },

  getCourseById: async (id: string): Promise<Course | null> => {
    console.log(`Fetching course with id: ${id}`);
    try {
      // Check if it's a sample course first
      if (id.startsWith('sample')) {
        const sampleCourse = sampleCourses.find(course => course.id === id);
        if (sampleCourse) {
          return sampleCourse;
        }
      }
      
      const response = await fetch(`${API_URL}/courses/${id}`);
      console.log('Response status:', response.status);
      
      if (!response.ok) {
        throw new Error(`Failed to fetch course details: ${response.status} ${response.statusText}`);
      }
      
      return await response.json();
    } catch (error) {
      console.error(`Error fetching course ${id}:`, error);
      // For sample/demo purposes, return a sample course if fetching fails
      return id.match(/\d/) ? sampleCourses[parseInt(id.match(/\d/)?.[0] || '0') % sampleCourses.length] : null;
    }
  },

  getPopularCourses: async (): Promise<Course[]> => {
    console.log('Fetching popular courses');
    try {
      const response = await fetch(`${API_URL}/courses/popular`);
      console.log('Response status:', response.status);
      
      if (!response.ok) {
        throw new Error(`Failed to fetch popular courses: ${response.status} ${response.statusText}`);
      }
      
      const data = await response.json();
      console.log('Received popular courses:', data.length > 0 ? `${data.length} courses` : 'No courses');
      
      // Return sample courses if the API returns empty array
      if (Array.isArray(data) && data.length === 0) {
        console.warn('API returned empty array, using sample courses instead');
        return sampleCourses;
      }
      
      return data;
    } catch (error) {
      console.error('Error fetching popular courses:', error);
      console.warn('Using sample courses as fallback');
      return sampleCourses;
    }
  },

  getCoursesByCategory: async (category: string): Promise<Course[]> => {
    console.log(`Fetching courses in category: ${category}`);
    try {
      if (category === 'all') {
        return courseService.getAllCourses();
      }
      
      const response = await fetch(`${API_URL}/courses/category/${category}`);
      console.log('Response status:', response.status);
      
      if (!response.ok) {
        throw new Error(`Failed to fetch courses in category: ${category} - ${response.status} ${response.statusText}`);
      }
      
      const data = await response.json();
      console.log(`Received ${category} courses:`, data.length > 0 ? `${data.length} courses` : 'No courses');
      
      // If API returns no courses for this category, filter sample courses
      if (Array.isArray(data) && data.length === 0) {
        console.warn('API returned empty array, using filtered sample courses instead');
        return sampleCourses.filter(course => 
          category === 'all' || course.category.toLowerCase() === category.toLowerCase()
        );
      }
      
      return data;
    } catch (error) {
      console.error(`Error fetching courses in category ${category}:`, error);
      console.warn('Using filtered sample courses as fallback');
      return sampleCourses.filter(course => 
        category === 'all' || course.category.toLowerCase() === category.toLowerCase()
      );
    }
  }
}; 