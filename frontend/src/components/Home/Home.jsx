import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Home = () => {
    const navigate = useNavigate();

    const handleSearch = () => {
        navigate('/search');
    };

    return (
        <div style={{ padding: '2rem', backgroundColor: '#990000', minHeight: '100vh', color: 'black' }}>
          <h1 style={{
              fontSize: '3rem',
           fontWeight: 'bold',
           color: 'white',
           marginBottom: '1rem'
          }}>
            Welcome,!
          </h1>

          <button
            onClick={handleSearch}
            style={{ fontSize: '1.5rem', marginBottom: '1rem', color: '#990000' }}
          >
           Search For Courses
          </button>
        </div>
    );
};
export default Home;


