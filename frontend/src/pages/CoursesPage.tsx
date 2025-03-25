import React, { useState, useEffect } from 'react';
import { courseService } from '../services/courseService';
import { Course } from '../types/course';
import CourseCard from '../components/courses/CourseCard';

const CoursesPage = () => {
  const [courses, setCourses] = useState<Course[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [filter, setFilter] = useState<string>('all');

  useEffect(() => {
    const fetchCourses = async () => {
      setLoading(true);
      let fetchedCourses: Course[] = [];
      
      if (filter === 'all') {
        fetchedCourses = await courseService.getAllCourses();
      } else {
        fetchedCourses = await courseService.getCoursesByCategory(filter);
      }
      
      setCourses(fetchedCourses);
      setLoading(false);
    };

    fetchCourses();
  }, [filter]);

  const categories = ['all', 'Programming', 'Web Development', 'Data Science', 'Security', 'Cloud Computing'];

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <h1 className="text-3xl font-bold mb-8">Available Courses</h1>
      
      {/* Category filter */}
      <div className="mb-8">
        <div className="flex flex-wrap gap-2">
          {categories.map((category) => (
            <button
              key={category}
              onClick={() => setFilter(category)}
              className={`px-4 py-2 rounded-full text-sm font-medium ${
                filter === category
                  ? 'bg-indigo-600 text-white'
                  : 'bg-gray-100 text-gray-800 hover:bg-gray-200'
              }`}
            >
              {category === 'all' ? 'All Courses' : category}
            </button>
          ))}
        </div>
      </div>
      
      {loading ? (
        <div className="flex justify-center items-center h-64">
          <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-indigo-500"></div>
        </div>
      ) : courses.length > 0 ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          {courses.map((course) => (
            <CourseCard key={course.id} course={course} />
          ))}
        </div>
      ) : (
        <div className="text-center py-12">
          <p className="text-xl text-gray-600">No courses found in this category</p>
        </div>
      )}
    </div>
  );
};

export default CoursesPage;