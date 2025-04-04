import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

interface Course {
  courseName: string;
  instructor: string;
  type: string;
}

const CoursesPage = () => {
  const [courses, setCourses] = useState<Course[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchCourses = async () => {
      try {
        console.log('Fetching courses...');
        const response = await fetch('http://localhost:8081/api/courses');
        
        if (!response.ok) {
          throw new Error(`Failed to fetch courses: ${response.status} ${response.statusText}`);
        }
        
        const data = await response.json();
        console.log('Courses fetched:', data); // Debug log
        setCourses(data);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Error fetching courses');
        console.error('Error fetching courses:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchCourses();
  }, []);

  // Function to get course image based on type
  const getCourseImage = (type: string, index: number) => {
    const imageMap: Record<string, string> = {
      'video': 'https://source.unsplash.com/random/800x600?video+learning',
      'text': 'https://source.unsplash.com/random/800x600?book+learning',
      'live': 'https://source.unsplash.com/random/800x600?classroom'
    };
    
    // Use index to ensure variety in images
    return `${imageMap[type] || 'https://source.unsplash.com/random/800x600?education'}/${index}`;
  };

  // Function to get course type badge
  const getCourseBadge = (type: string) => {
    const badges: Record<string, { color: string, text: string }> = {
      'video': { color: 'bg-blue-100 text-blue-800', text: 'Video Course' },
      'text': { color: 'bg-green-100 text-green-800', text: 'Text Course' },
      'live': { color: 'bg-purple-100 text-purple-800', text: 'Live Course' }
    };
    
    const badge = badges[type] || { color: 'bg-gray-100 text-gray-800', text: 'Course' };
    
    return (
      <span className={`${badge.color} px-2 py-1 rounded-full text-xs font-medium`}>
        {badge.text}
      </span>
    );
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <h1 className="text-3xl font-bold mb-8">Available Courses</h1>
      
      {loading && (
        <div className="flex justify-center">
          <p className="text-gray-500">Loading courses...</p>
        </div>
      )}
      
      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
          <p>{error}</p>
        </div>
      )}
      
      {!loading && !error && courses.length === 0 && (
        <div className="text-center py-8">
          <p className="text-gray-500">No courses available at the moment.</p>
        </div>
      )}
      
      {!loading && !error && courses.length > 0 && (
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {courses.map((course, index) => (
            <div key={index} className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow">
              <img 
                src={getCourseImage(course.type, index)}
                alt={course.courseName}
                className="w-full h-48 object-cover"
              />
              <div className="p-6">
                <div className="flex justify-between items-start mb-2">
                  <h3 className="text-xl font-semibold">{course.courseName}</h3>
                  {getCourseBadge(course.type)}
                </div>
                <p className="text-gray-600 mb-4">Instructor: {course.instructor}</p>
                <Link
                  to={`/courses/${index + 1}`}
                  className="text-indigo-600 font-semibold hover:text-indigo-800"
                >
                  View Details →
                </Link>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default CoursesPage;