import React from 'react';
import { Link } from 'react-router-dom';
import { Course } from '../../types/course';

interface CourseCardProps {
  course: Course;
}

const CourseCard: React.FC<CourseCardProps> = ({ course }) => {
  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden transition-all hover:shadow-lg">
      <img
        src={course.imageUrl}
        alt={course.title}
        className="w-full h-48 object-cover"
      />
      <div className="p-6">
        <div className="flex justify-between items-start mb-2">
          <h3 className="text-xl font-semibold text-gray-900">{course.title}</h3>
          <span className="bg-indigo-100 text-indigo-800 text-xs font-medium px-2.5 py-0.5 rounded">
            {course.category}
          </span>
        </div>
        
        <p className="text-gray-600 mb-4 line-clamp-2">{course.description}</p>
        
        <div className="flex items-center mb-4">
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
            <span className="ml-1 text-gray-500 text-sm">{course.rating.toFixed(1)}</span>
          </div>
          <span className="mx-2 text-gray-300">|</span>
          <span className="text-gray-500 text-sm">{course.enrolledStudents} students</span>
        </div>
        
        <div className="flex items-center justify-between">
          <div className="flex items-center">
            <span className="text-gray-500 text-sm mr-2">{course.durationHours} hours</span>
            <span className="text-gray-500 text-sm">{course.difficulty}</span>
          </div>
          <span className="text-2xl font-bold text-gray-900">${course.price.toFixed(2)}</span>
        </div>
        
        <Link
          to={`/courses/${course.id}`}
          className="mt-4 inline-block w-full text-center bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded-md transition-colors"
        >
          View Course
        </Link>
      </div>
    </div>
  );
};

export default CourseCard; 