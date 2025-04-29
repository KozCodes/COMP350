import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import Sidebar from './sidebar';

axios.defaults.withCredentials = true;

const Results = () => {
  const location = useLocation();
  const [courses] = useState(location.state);
  const [ratings, setRatings] = useState({});
  const [messages, setMessages] = useState({});
  const [currentSchedule, setCurrentSchedule] = useState([]);
  const [addedCourses, setAddedCourses] = useState(new Set());

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
          .catch(() => ({ id, score: null }))
      );

      const ratingResults = await Promise.all(ratingPromises);
      const newRatings = {};
      ratingResults.forEach(({ id, score }) => {
        newRatings[id] = score;
      });

      setRatings(newRatings);
    };

    const fetchCurrentSchedule = async () => {
      try {
        const res = await axios.get('http://localhost:8080/api/currentSchedule');
        const schedule = Array.isArray(res.data) ? res.data : [];
        setCurrentSchedule(schedule);

        const updatedMessages = {};
        const updatedAddedCourses = new Set();

        const scheduledCourseIds = new Set(schedule.map(c => {
          const parsed = typeof c === 'string' ? JSON.parse(c) : c;
          return parsed.id;
        }));

        courses.forEach(courseJson => {
          const course = JSON.parse(courseJson);
          const conflict = getConflictCourse(course, schedule);
          if (conflict) {
            updatedMessages[course.id] = `This class has a time conflict with another on the schedule!`;
          } else {
            updatedMessages[course.id] = '';
          }

          if (scheduledCourseIds.has(course.id)) {
            updatedAddedCourses.add(course.id);
          }
        });

        setMessages(updatedMessages);
        setAddedCourses(updatedAddedCourses);
      } catch (error) {
        console.error('Error fetching current schedule:', error);
      }
    };

    fetchRatings();
    fetchCurrentSchedule();
  }, [courses]);

  const timeConflict = (courseA, courseB) => {
    const daysA = new Set(courseA.courseDays);
    const daysB = new Set(courseB.courseDays);
    const sharedDays = [...daysA].filter(day => daysB.has(day));
    if (sharedDays.length === 0) return false;

    for (let i = 0; i < courseA.startTime.length; i++) {
      const startA = courseA.startTime[i];
      const endA = courseA.endTime[i];

      for (let j = 0; j < courseB.startTime.length; j++) {
        const startB = courseB.startTime[j];
        const endB = courseB.endTime[j];

        if (startA < endB && startB < endA) {
          return true;
        }
      }
    }

    return false;
  };

  const getConflictCourse = (newCourse, schedule = currentSchedule) => {
    if (!Array.isArray(schedule)) return null;
    for (const scheduled of schedule) {
      const parsed = typeof scheduled === 'string' ? JSON.parse(scheduled) : scheduled;
      if (timeConflict(parsed, newCourse)) {
        return parsed;
      }
    }
    return null;
  };

const handleAddToSchedule = async (e, course) => {
  const courseId = parseInt(e.target.value);  // 🔥 FIX: make sure it's a number

  const conflictingCourse = getConflictCourse(course);
  if (conflictingCourse) {
    setMessages((prevMessages) => ({
      ...prevMessages,
      [courseId]: `This class has a time conflict with another on the schedule!`
    }));
    return;
  }

  try {
    await axios.post('http://localhost:8080/api/register-course', {
      Id: courseId
    });
    setMessages((prevMessages) => ({
      ...prevMessages,
      [courseId]: 'Course successfully added to your schedule!'
    }));
    setCurrentSchedule(prev => [...prev, course]);
    setAddedCourses(prev => new Set(prev).add(course.id));
  } catch (error) {
    setMessages((prevMessages) => ({
      ...prevMessages,
      [courseId]: 'Failed to add course to schedule. Please try again.'
    }));
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
      <div style={pageContentStyle}>
        <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
          <h1 style={{ fontSize: '2.5rem', fontWeight: 'bold', color: '#990000' }}>
            Course Search Results
          </h1>
        </div>

        <div style={{ display: 'flex' }}>
          <Sidebar />
          <div style={{ marginLeft: '250px', flex: 1 }}></div>
        </div>

        <div style={{ width: '100%', maxWidth: '800px' }}>
          {courses.length === 0 ? (
            <p style={{ textAlign: 'center', color: '#555' }}>No courses available.</p>
          ) : (
            courses.map((courseJson, index) => {
              const course = JSON.parse(courseJson);
              const profRating = ratings[course.professor.id];
              const seatsAvailable = (course.numSeats || 0) - (course.numRegistered || 0);
              const hasConflict = addedCourses.has(course.id) ? null : getConflictCourse(course);

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
                  <p style={infoStyle}><strong>Days:</strong> {course.courseDays.join(', ')}</p>
                  <p style={infoStyle}>
                    <strong>Time:</strong> {course.startTime.join(', ')} - {course.endTime.join(', ')}
                  </p>
                  <p style={infoStyle}><strong>Session:</strong> {course.session} {course.year}</p>
                  <p style={infoStyle}><strong>Seats Available:</strong> {seatsAvailable}</p>

                  {!addedCourses.has(course.id) && hasConflict ? (
                    <p style={{ color: 'red', fontWeight: 'bold' }}>
                      This class has a time conflict with another on the schedule!
                    </p>
                  ) : (
                    <>
                      {!addedCourses.has(course.id) && (
                        <button
                          value={course.id}
                          onClick={(e) => handleAddToSchedule(e, course)}
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
                      )}
                      {messages[course.id] && (
                        <p style={{ color: messages[course.id].startsWith('Course successfully') ? 'green' : 'red', marginTop: '0.5rem' }}>
                          {messages[course.id]}
                        </p>
                      )}
                    </>
                  )}
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
