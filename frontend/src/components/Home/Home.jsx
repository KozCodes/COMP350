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
       fetchSchedules();
   }, []);

   const handleNavigation = (path) => {
       navigate(path);
   };

   return (
       <div style={{ padding: '2rem', backgroundColor: 'white', minHeight: '100vh', color: 'black' }}>
           <h1 style={{
               fontSize: '3rem',
               fontWeight: 'bold',
               color: '#990000',
               marginBottom: '2rem'
           }}>
               Welcome, {studentName}!
           </h1>
           <div className="container mt-4">
               <h2 className="text-center text-dark mb-4">Your Schedules</h2>
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
       </div>
   );
};

export default Home;