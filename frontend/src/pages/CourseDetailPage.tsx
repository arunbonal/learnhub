import React from 'react';
import { useParams } from 'react-router-dom';

const CourseDetailPage = () => {
  const { id } = useParams();

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <h1 className="text-3xl font-bold mb-8">Course Details</h1>
      <p>Course ID: {id}</p>
    </div>
  );
};

export default CourseDetailPage;