import React, { useState, useEffect } from 'react';
          import { useNavigate, Link } from 'react-router-dom';
          import axios from 'axios';

          const Home = () => {
              const [studentName, setStudentName] = useState("Guest");
              const [schedules, setSchedules] = useState([]);
              const [selectedScheduleId, setSelectedScheduleId] = useState(null);
              const [selectedScheduleName, setSelectedScheduleName] = useState(""); // New state for selected schedule name
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

              const handleScheduleClick = async (scheduleId) => {
                  try {
                      await axios.post("http://localhost:8080/api/setCurrentSchedule", { scheduleId });
                      setSelectedScheduleId(scheduleId);

                      // Find the selected schedule name
                      const selectedSchedule = schedules.find(schedule => schedule.id === scheduleId);
                      setSelectedScheduleName(selectedSchedule ? selectedSchedule.name : ""); // Update the name
                  } catch (error) {
                      console.error("Error setting current schedule:", error);
                  }
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
                          {selectedScheduleName && (
                              <span style={{ fontSize: '1.5rem', marginLeft: '1rem', color: '#007bff' }}>
                                  (Selected Schedule: {selectedScheduleName})
                              </span>
                          )}
                      </h1>
                      <div className="container mt-4">
                          <h2 className="text-center text-dark mb-4">Your Schedules</h2>
                          <div className="row">
                              {schedules.map((schedule) => (
                                  <div
                                      className={`col-12 col-sm-6 col-md-4 col-lg-3 mb-4`}
                                      key={schedule.id}
                                      onClick={() => handleScheduleClick(schedule.id)}
                                      style={{
                                          cursor: 'pointer',
                                          border: selectedScheduleId === schedule.id ? '2px solid #007bff' : 'none',
                                          borderRadius: selectedScheduleId === schedule.id ? '15px' : '0',
                                          padding: '5px'
                                      }}
                                  >
                                      <div className="card text-center">
                                          <img
                                              src="https://edurank.org/assets/img/uni-logos/grove-city-college-logo.png"
                                              alt="Schedule Logo"
                                              className="card-img-top img-fluid"
                                              style={{ maxHeight: '150px', objectFit: 'contain' }}
                                          />
                                          <div className="card-body">
                                              <h5 className="card-title">
                                                  <Link to={`/schedule/${schedule.id}`} style={{ textDecoration: 'none', color: '#007bff' }}>
                                                      {schedule.name}
                                                  </Link>
                                              </h5>
                                              <p className="card-text text-muted">
                                                  {schedule.session === "Empty"
                                                      ? `${schedule.session}`
                                                      : `${schedule.session} ${schedule.year}`}
                                              </p>
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