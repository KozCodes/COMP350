import React, {useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import axios from 'axios';

const Results = () => {
    const location = useLocation();
    const navigate = useNavigate();


    const data = location.state;

   const [courses, setCourses] = useState(data);

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
      <div>
      <div style={{display: 'flex', flexDirection: 'row', alignItems: 'flex-start', justifyContent: 'space-evenly', gap: '10px', padding: '2rem'}}>
         <button style = {{fontWeight: 'bold', backgroundColor: '#990000', color: 'white'}} onClick={() => navigate('/Home')}> Home
         </button>
         <button style = {{fontWeight: 'bold', backgroundColor: '#990000', color: 'white'}} onClick={() => navigate('/Home')}> See Schedule

          </button>
           <button style = {{fontWeight: 'bold', backgroundColor: '#990000', color: 'white'}} onClick={() => navigate('/search')}> Search Again

                    </button>
      </div>
    <div className="p-4 max-w-xl mx-auto">
      <h2 style= {{color: '#990000'}} className="text-2xl font-bold mb-4">Course Search Results </h2>
      {courses.length === 0 ? (
        <p>No courses available.</p>
      ) : (
        <ul className="space-y-4">
          {courses.map((course) => (
            <li key={JSON.parse(course).id} className="p-4 border rounded-lg shadow-sm">
                <div style={{display: 'flex', flexDirection: 'row', alignItems: 'flex-start', justifyContent: 'space-between', gap: '10px', padding: '2rem'}}>
              <h3 style= {{color: '#990000'}} className="text-lg font-semibold">{JSON.parse(course).courseTitle}</h3>
                <p className="text-sm text-gray-600">
                    {JSON.parse(course).courseCode}
                </p>
              </div>
             <div style={{display: 'flex', flexDirection: 'row', alignItems: 'flex-start', justifyContent: 'space-evenly', gap: '10px', padding: '2rem'}}>
               <p className="text-sm text-gray-600">
                                  {JSON.parse(course).courseDays}
               </p>
              <p className="text-sm text-gray-600">
                    {JSON.parse(course).startTime.filter((value, index, self) => self.indexOf(value) === index)}
             </p>
              <p className="text-sm text-gray-600">
                  {JSON.parse(course).endTime.filter((value, index, self) => self.indexOf(value) === index)}
              </p>
              <p className="text-sm text-gray-600">
                  {JSON.parse(course).professor.name}, {JSON.parse(course).professor.score}
              </p>
               <p className="text-sm text-gray-600">
                   {JSON.parse(course).session} {JSON.parse(course).year}
               </p>
             </div>
              <p className="text-sm text-gray-600">
                Seats: {JSON.parse(course).numRegistered}/{JSON.parse(course).numSeats}
              </p>
              <button
                className="mt-2 px-3 py-1 bg-blue-600 text-white rounded hover:bg-blue-700 disabled:bg-gray-400"
                disabled={course.numRegistered >= course.numSeats}
                onClick={() => handleAddCourse(JSON.parse(course).id)}
              >
                {JSON.parse(course).numRegistered >= JSON.parse(course).numSeats ? 'Full' : 'Add to Schedule'}
              </button>
            </li>
          ))}
        </ul>

      )}
    </div>
    </div>
  );
};

export default Results;