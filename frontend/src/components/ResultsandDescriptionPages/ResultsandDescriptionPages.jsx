import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import Sidebar from './sidebar';

axios.defaults.withCredentials = true;

const Results = () => {
  const location = useLocation();
  const [courses] = useState(location.state);
  const [ratings, setRatings] = useState({});
  const [message, setMessage] = useState('');


  useEffect(() => {
    const fetchRatings = async () => {
      const professorIds = new Set();
      courses.forEach(courseJson => {
        const course = JSON.parse(courseJson);
        professorIds.add(course.professor.id);
      });

      const ratingPromises = Array.from(professorIds).map(id =>
        axios.get(`http://localhost:8080/api/professor/${id}/rating`)
          .then(res => ({ id, score: res.data }))
          .catch(err => ({ id, score: null }))
      );

      const ratingResults = await Promise.all(ratingPromises);
      const newRatings = {};
      ratingResults.forEach(({ id, score }) => {
        newRatings[id] = score;
      });

      setRatings(newRatings);
    };

    fetchRatings();
  }, [courses]);

    const handleAddToSchedule = async (course) => {
        console.log(course.target.value);
      try {
        const response = await axios.post('http://localhost:8080/api/register-course', {
           Id: course.target.value
       });
        setMessage(response.data);
      } catch (error) {
        setMessage('Failed to add course to schedule. Please try again.');
      }
    };


  const renderStars = (score) => {
    if (score === null || score === undefined) return 'No rating yet';
    const fullStars = Math.floor(score);
    const hasHalf = score % 1 >= 0.5;
    const emptyStars = 5 - fullStars - (hasHalf ? 1 : 0);

    return (
      <div style={{ fontSize: '1.2rem', color: '#FFD700' }}>
        {'★'.repeat(fullStars)}{hasHalf ? '½' : ''}{'☆'.repeat(emptyStars)}
      </div>
    );
  };

  return (
    <div style={{ backgroundColor: '#f9f9f9', minHeight: '100vh' }}>

      {/* Page Content */}
      <div style={pageContentStyle}>
        <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
          <h1 style={{ fontSize: '2.5rem', fontWeight: 'bold', color: '#990000' }}>
            Course Search Results
          </h1>
        </div>

        {/* Sidebar */}
        <div style={{ display: 'flex' }}>
          <Sidebar />
          <div style={{ marginLeft: '250px', flex: 1 }}>
            {/* Main content here */}
          </div>
        </div>

        <div style={{ width: '100%', maxWidth: '800px' }}>
          {courses.length === 0 ? (
            <p style={{ textAlign: 'center', color: '#555' }}>No courses available.</p>
          ) : (
            courses.map((courseJson, index) => {
              const course = JSON.parse(courseJson);
              const profRating = ratings[course.professor.id];

              const numRegistered = course.numRegistered || 0;
              const numSeats = course.numSeats || 0;
              const seatsAvailable = numSeats - numRegistered;

              return (
                <div key={index} style={courseCardStyle}>
                  <h2 style={{ marginBottom: '0.5rem', color: '#222' }}>{course.courseTitle}</h2>

                  <p style={infoStyle}><strong>Code:</strong> {course.courseCode}</p>

                  <p style={infoStyle}>
                    <strong>Professor:</strong> {course.professor.name}
                    <span style={ratingStyle}>
                      ({profRating !== null && profRating !== undefined ? profRating.toFixed(2) : 'N/A'})
                    </span>
                  </p>

                  <div style={{ marginLeft: '1rem', marginBottom: '0.5rem' }}>
                    {renderStars(profRating)}
                  </div>

                  <p style={infoStyle}><strong>Days:</strong> {course.courseDays}</p>
                  <p style={infoStyle}>
                    <strong>Time:</strong> {Array.from(new Set(course.startTime)).join(", ")} - {Array.from(new Set(course.endTime)).join(", ")}
                  </p>
                  <p style={infoStyle}><strong>Session:</strong> {course.session} {course.year}</p>
                  <p style={{ margin: '0.25rem 0', color: '#666' }}>
                  </p>
                    <p style={infoStyle} > {message} </p>
                  <button
                    value={course.id}
                    onClick={handleAddToSchedule}
                    style={{
                      marginTop: '1rem',
                      backgroundColor: '#0070f3',
                      color: 'white',
                      padding: '0.6rem 1.2rem',
                      border: 'none',
                      borderRadius: '8px',
                      fontWeight: 'bold',
                      cursor: 'pointer'
                    }}
                  >
                    Add to Schedule
                  </button>
                </div>
              );
            })
          )}
        </div>
      </div>
    </div>
  );
};

// Styles
const pageContentStyle = {
  padding: '2rem',
  fontFamily: 'Segoe UI, sans-serif',
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center'
};

const courseCardStyle = {
  backgroundColor: 'white',
  borderRadius: '12px',
  padding: '1.5rem',
  marginBottom: '1.5rem',
  boxShadow: '0px 4px 12px rgba(0,0,0,0.05)'
};

const infoStyle = {
  margin: '0.25rem 0',
  color: '#666'
};

const ratingStyle = {
  fontSize: '0.9rem',
  color: '#990000',
  fontWeight: 'bold',
  marginLeft: '0.5rem'
};

export default Results;
