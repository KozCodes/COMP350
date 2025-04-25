import React, { useState, useEffect } from 'react';
    import { useNavigate } from 'react-router-dom';
    import axios from 'axios';

    const Home = () => {
       const [studentName, setStudentName] = useState("Guest");
       const [schedules, setSchedules] = useState([]);
       const navigate = useNavigate();

       useEffect(() => {
           const fetchStudentName = async () => {
               try {
                   const response = await axios.get("http://localhost:8080/api/currentStudentName");
                   setStudentName(response.data);
               } catch (error) {
                   console.error("Error fetching student name:", error);
               }
           };

           const fetchSchedules = async () => {
               try {
                   const response = await axios.get("http://localhost:8080/api/studentSchedules");
                   setSchedules(response.data);
               } catch (error) {
                   console.error("Error fetching schedules:", error);
               }
           };

           fetchStudentName();
           fetchSchedules(); // Call fetchSchedules here
       }, []);

       const handleNavigation = (path) => {
           navigate(path);
       };

       return (
           <div style={{ padding: '2rem', backgroundColor: '#990000', minHeight: '100vh', color: 'black', position: 'relative' }}>
               <button
                   onClick={() => handleNavigation('/login')}
                   style={logoutButtonStyle}
               >
                   Logout
               </button>

               <h1 style={{
                   fontSize: '3rem',
                   fontWeight: 'bold',
                   color: 'white',
                   marginBottom: '2rem'
               }}>
                   Welcome, {studentName}!
               </h1>
               <div className="container mt-4">
                   <h2 className="text-center text-white mb-4">Your Schedules</h2>
                   <div className="row">
                       {schedules.map((schedule) => (
                           <div className="col-12 col-sm-6 col-md-4 col-lg-3 mb-4" key={schedule.id}>
                               <div className="card text-center">
                                   <img
                                       src="https://edurank.org/assets/img/uni-logos/grove-city-college-logo.png"
                                       alt="Schedule Logo"
                                       className="card-img-top img-fluid"
                                       style={{ maxHeight: '150px', objectFit: 'contain' }}
                                   />
                                   <div className="card-body">
                                       <h5 className="card-title">{schedule.name}</h5>
                                   </div>
                               </div>
                           </div>
                       ))}
                   </div>
               </div>

               <div style={{
                   display: 'flex',
                   flexWrap: 'wrap',
                   gap: '1.5rem',
                   justifyContent: 'center'
               }}>
                   <button
                       onClick={() => handleNavigation('/search')}
                       style={buttonStyle}
                   >
                       Search Courses
                   </button>
                   <button
                       onClick={() => handleNavigation('/auto-scheduler')}
                       style={buttonStyle}
                   >
                       Schedule Generator
                   </button>
               </div>
           </div>
       );
    };

    const buttonStyle = {
       backgroundColor: 'white',
       color: '#990000',
       padding: '1rem 2rem',
       borderRadius: '12px',
       fontSize: '1.2rem',
       fontWeight: 'bold',
       border: '2px solid #990000',
       cursor: 'pointer',
       transition: 'transform 0.3s, background-color 0.3s',
       textTransform: 'uppercase',
       boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.2)',
       textAlign: 'center'
    };

    const logoutButtonStyle = {
       position: 'absolute',
       top: '1rem',
       right: '1rem',
       backgroundColor: '#990000',
       color: 'white',
       padding: '0.8rem 1.5rem',
       borderRadius: '8px',
       fontSize: '1rem',
       fontWeight: 'bold',
       border: 'none',
       cursor: 'pointer',
       transition: 'background-color 0.3s ease'
    };

    export default Home;