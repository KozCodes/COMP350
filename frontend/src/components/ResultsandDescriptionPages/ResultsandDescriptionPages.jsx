import React, {useState, useEffect} from 'react';
import axios from 'axios';

const initialCourses = [
  { id: 1, title: 'Intro to Programming', numSeats: 2, numRegistered: 0 },
  { id: 2, title: 'Data Structures', numSeats: 2, numRegistered: 1 },
  { id: 3, title: 'Operating Systems', numSeats: 2, numRegistered: 2 },
];

const Results = () => {
  const [courses, setCourses] = useState(initialCourses);

  const handleAddCourse = (courseId) => {
    setCourses((prevCourses) =>
      prevCourses.map((course) =>
        course.id === courseId && course.numRegistered < course.numSeats
          ? { ...course, numRegistered: course.numRegistered + 1 }
          : course
      )
    );
  };

  return (
    <div className="p-4 max-w-xl mx-auto">
      <h2 className="text-2xl font-bold mb-4">Course Search Results (Mock)</h2>
      {courses.length === 0 ? (
        <p>No courses available.</p>
      ) : (
        <ul className="space-y-4">
          {courses.map((course) => (
            <li key={course.id} className="p-4 border rounded-lg shadow-sm">
              <h3 className="text-lg font-semibold">{course.title}</h3>
              <p className="text-sm text-gray-600">
                Seats: {course.numRegistered}/{course.numSeats}
              </p>
              <button
                className="mt-2 px-3 py-1 bg-blue-600 text-white rounded hover:bg-blue-700 disabled:bg-gray-400"
                disabled={course.numRegistered >= course.numSeats}
                onClick={() => handleAddCourse(course.id)}
              >
                {course.numRegistered >= course.numSeats ? 'Full' : 'Add to Schedule'}
              </button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default Results;