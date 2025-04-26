import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';

axios.defaults.withCredentials = true;

const Results = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [courses, setCourses] = useState(location.state);
  const [ratings, setRatings] = useState({});

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

  // Handle course registration
  const handleAddCourse = async (courseId) => {
    try {
      const response = await axios.post(
        'http://localhost:8080/api/register-course',
        null, // No body, since we're passing `courseId` as a URL parameter
        { params: { courseId } } // This sends `courseId=139` as the query parameter
      );
      alert(response.data); // Success message from backend
    } catch (error) {
      const errorMessage = error.response ? error.response.data : error.message;
      alert(`Error: ${errorMessage}`); // Display error from backend
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
      {/* Navigation Bar */}
      <div style={navBarStyle}>
        <div
          style={homeTitleStyle}
          onClick={() => navigate('/Home')}
        >
          GCC Home
        </div>
        <div style={{ display: 'flex', gap: '1rem' }}>
          <button onClick={() => navigate('/Home')} style={navButtonStyle}>See Schedule</button>
          <button onClick={() => navigate('/search')} style={navButtonStyle}>Search Again</button>
        </div>
      </div>

      {/* Page Content */}
      <div style={pageContentStyle}>
        <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
          <h1 style={{ fontSize: '2.5rem', fontWeight: 'bold', color: '#990000' }}>
            Course Search Results
          </h1>
        </div>

        <div style={{ width: '100%', maxWidth: '800px' }}>
          {courses.length === 0 ? (
            <p style={{ textAlign: 'center', color: '#555' }}>No courses available.</p>
          ) : (
            courses.map((courseJson, index) => {
              const course = JSON.parse(courseJson);
              const profRating = ratings[course.professor.id];

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
                    <strong>Seats:</strong> {course.numRegistered || 0}/{course.numSeats || 0}
                  </p>

                  <button
                    onClick={() => handleAddCourse(course.id)}
                    disabled={course.numRegistered >= course.numSeats}
                    style={{
                      marginTop: '1rem',
                      backgroundColor: course.numRegistered >= course.numSeats ? '#ccc' : '#0070f3',
                      color: 'white',
                      padding: '0.6rem 1.2rem',
                      border: 'none',
                      borderRadius: '8px',
                      fontWeight: 'bold',
                      cursor: course.numRegistered >= course.numSeats ? 'not-allowed' : 'pointer'
                    }}
                  >
                    {course.numRegistered >= course.numSeats ? 'Full' : 'Add to Schedule'}
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
const navBarStyle = {
  backgroundColor: '#990000',
  color: 'white',
  padding: '1rem 2rem',
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center',
  position: 'sticky',
  top: 0,
  zIndex: 1000,
  boxShadow: '0px 2px 5px rgba(0,0,0,0.1)'
};

const homeTitleStyle = {
  fontSize: '1.5rem',
  fontWeight: 'bold',
  cursor: 'pointer'
};

const navButtonStyle = {
  backgroundColor: 'white',
  color: '#990000',
  fontWeight: 'bold',
  padding: '0.5rem 1rem',
  borderRadius: '6px',
  border: 'none',
  cursor: 'pointer'
};

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
