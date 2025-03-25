import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { courseService } from '../services/courseService';
import { Course } from '../types/course';
import { BookOpen, Clock, Award, Users } from 'lucide-react';

const CourseDetailPage = () => {
  const { id } = useParams<{ id: string }>();
  const [course, setCourse] = useState<Course | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchCourseDetails = async () => {
      if (!id) return;
      
      setLoading(true);
      try {
        const courseData = await courseService.getCourseById(id);
        setCourse(courseData);
      } catch (err) {
        setError('Failed to load course details');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchCourseDetails();
  }, [id]);

  if (loading) {
    return (
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12 flex justify-center">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-indigo-500"></div>
      </div>
    );
  }

  if (error || !course) {
    return (
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="text-center py-12">
          <h2 className="text-2xl font-bold text-red-600 mb-4">Error</h2>
          <p className="text-lg text-gray-600">{error || 'Course not found'}</p>
          <Link
            to="/courses"
            className="mt-6 inline-block bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2 px-6 rounded-md"
          >
            Back to Courses
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <div className="bg-white rounded-lg shadow-md overflow-hidden">
        {/* Course header */}
        <div className="relative h-64 md:h-80">
          <img
            src={course.imageUrl}
            alt={course.title}
            className="w-full h-full object-cover"
          />
          <div className="absolute inset-0 bg-black bg-opacity-40 flex items-end">
            <div className="p-6 md:p-8 w-full">
              <div className="flex flex-wrap gap-2 mb-3">
                <span className="bg-indigo-100 text-indigo-800 text-xs font-medium px-2.5 py-0.5 rounded">
                  {course.category}
                </span>
                <span className="bg-blue-100 text-blue-800 text-xs font-medium px-2.5 py-0.5 rounded">
                  {course.difficulty}
                </span>
              </div>
              <h1 className="text-white text-2xl md:text-4xl font-bold mb-2">
                {course.title}
              </h1>
              <p className="text-gray-200 text-lg">
                Instructor: {course.instructor}
              </p>
            </div>
          </div>
        </div>

        <div className="p-6 md:p-8">
          <div className="flex flex-wrap gap-6 md:gap-12 mb-8 text-center">
            <div className="flex flex-col items-center">
              <Users className="h-6 w-6 text-indigo-600 mb-2" />
              <span className="text-gray-700">{course.enrolledStudents} Students</span>
            </div>
            <div className="flex flex-col items-center">
              <Clock className="h-6 w-6 text-indigo-600 mb-2" />
              <span className="text-gray-700">{course.durationHours} Hours</span>
            </div>
            <div className="flex flex-col items-center">
              <Award className="h-6 w-6 text-indigo-600 mb-2" />
              <div className="flex items-center">
                {[...Array(5)].map((_, i) => (
                  <svg
                    key={i}
                    className={`w-4 h-4 ${
                      i < Math.floor(course.rating) ? 'text-yellow-400' : 'text-gray-300'
                    }`}
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="currentColor"
                    viewBox="0 0 22 20"
                  >
                    <path d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z" />
                  </svg>
                ))}
                <span className="ml-1">{course.rating.toFixed(1)}</span>
              </div>
            </div>
            <div className="flex flex-col items-center">
              <BookOpen className="h-6 w-6 text-indigo-600 mb-2" />
              <span className="text-gray-700">{course.topics.length} Modules</span>
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div className="md:col-span-2">
              <h2 className="text-2xl font-bold mb-4">Course Description</h2>
              <p className="text-gray-700 mb-8 whitespace-pre-line">
                {course.description}
              </p>

              <h2 className="text-2xl font-bold mb-4">What You'll Learn</h2>
              <ul className="grid grid-cols-1 md:grid-cols-2 gap-2 mb-8">
                {course.topics.map((topic, index) => (
                  <li key={index} className="flex items-start">
                    <svg className="w-4 h-4 text-green-500 mt-1 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 13l4 4L19 7"></path>
                    </svg>
                    <span>{topic}</span>
                  </li>
                ))}
              </ul>
            </div>

            <div>
              <div className="bg-gray-50 p-6 rounded-lg shadow-sm sticky top-6">
                <div className="text-3xl font-bold text-center mb-6">
                  ${course.price.toFixed(2)}
                </div>
                <button className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded-md mb-3">
                  Enroll Now
                </button>
                <p className="text-center text-sm text-gray-500 mb-4">
                  30-Day Money-Back Guarantee
                </p>
                <div className="border-t border-gray-200 pt-4">
                  <h3 className="font-semibold mb-2">This course includes:</h3>
                  <ul className="space-y-2">
                    <li className="flex items-center text-sm">
                      <svg className="w-4 h-4 text-indigo-600 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                      </svg>
                      <span>{course.durationHours} hours on-demand video</span>
                    </li>
                    <li className="flex items-center text-sm">
                      <svg className="w-4 h-4 text-indigo-600 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                      </svg>
                      <span>Full lifetime access</span>
                    </li>
                    <li className="flex items-center text-sm">
                      <svg className="w-4 h-4 text-indigo-600 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                      </svg>
                      <span>Certificate of completion</span>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CourseDetailPage;