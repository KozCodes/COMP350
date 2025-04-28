import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Sidebar = () => {
  const [currentSchedule, setCurrentSchedule] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCurrentSchedule = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/currentSchedule');
        setCurrentSchedule(response.data);
      } catch (error) {
        console.error('Error fetching current schedule:', error);
      }
    };
    fetchCurrentSchedule();
  }, []);

  const removeCourse = async (courseId) => {
    try {
      await axios.delete(`http://localhost:8080/api/course/delete?scheduleid=${currentSchedule.id}&&courseid=${courseId}`);
      setCurrentSchedule((prevSchedule) => ({
        ...prevSchedule,
        classes: prevSchedule.classes.filter((course) => course.id !== courseId),
      }));
    } catch (error) {
      console.error('Error removing course:', error);
    }
  };

  return (
    <div style={sidebarStyle}>
      {currentSchedule && currentSchedule.name ? (
        <h2 style={{ color: '#fff', marginBottom: '1rem' }}>{currentSchedule.name}</h2>
      ) : ( <h2 style={{ color: '#fff', marginBottom: '1rem' }}>No schedule selected</h2>)}

      {currentSchedule && currentSchedule.classes && currentSchedule.classes.length > 0 ? (
        currentSchedule.classes.map((course) => (
          <div key={course.id} style={courseItemStyle}>
            <p style={{ margin: '0', color: '#fff' }}>{course.courseTitle}</p>
            <p style={{ margin: '0', color: '#ccc' }}>{course.courseCode}</p>
            <button
              onClick={() => removeCourse(course.id)}
              style={removeButtonStyle}
            >
              X
            </button>
          </div>
        ))
      ) : (
        <p style={{ color: '#ccc' }}>No courses in schedule.</p>
      )}

      <div style={buttonContainerStyle}>
        <button onClick={() => navigate('/Home')} style={navButtonStyle}>See Schedule</button>
        <button onClick={() => navigate('/search')} style={navButtonStyle}>Search Again</button>
      </div>
    </div>
  );
};

// Styles
const sidebarStyle = {
  width: '250px',
  backgroundColor: '#dc3545',
  padding: '1rem',
  position: 'fixed',
  top: 'var(--nav-bar-height, 60px)',
  left: 0,
  display: 'flex',
  flexDirection: 'column', // Ensures content is stacked vertically
  height: '100vh', // Adjust height to account for the myGCC bar,
  overflowY: 'auto',
  boxShadow: '2px 0 5px rgba(0,0,0,0.1)',
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

const buttonContainerStyle = {
  marginTop: 'auto', // Pushes the buttons to the bottom
  display: 'flex',
  gap: '1rem',
  paddingBottom: '3rem', // Adds space above the bottom of the page
};

const courseItemStyle = {
  marginBottom: '1rem',
  padding: '0.5rem',
  backgroundColor: '#920000',
  borderRadius: '8px',
  position: 'relative', // Enable positioning for the remove button
};

const removeButtonStyle = {
  backgroundColor: 'transparent',
  color: '#fff',
  border: '1px solid #fff', // Add a border around the button
  fontSize: '1rem',
  cursor: 'pointer',
  borderRadius: '50%', // Make the button circular
  width: '15px', // Set width for the button
  height: '15px', // Set height for the button
  position: 'absolute', // Position the button within the course item
  top: '8px', // Position it in the top-right corner
  right: '8px',
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
};

export default Sidebar;