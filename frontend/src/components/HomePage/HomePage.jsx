import React from 'react';
import { useNavigate } from 'react-router-dom';

const HomePage = () => {
    const navigate = useNavigate();

    const handleLogin = () => {
        navigate('/login');
    };

    return (
        <div style={{ padding: '2rem', backgroundColor: '#990000', minHeight: '100vh', color: 'black' }}>
            <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
                {/* Welcome Title */}
                <h1 style={{
                    fontSize: '3rem',
                    fontWeight: 'bold',
                    color: 'white',
                    marginBottom: '1rem'
                }}>
                    Welcome to GCC Schedule Aid!
                </h1>

                {/* Subtitle */}
                <p style={{
                    fontSize: '1.25rem',
                    fontWeight: 'bold',
                    color: 'white',
                    marginBottom: '2rem'
                }}>
                    Explore create and rate at Grove City College!
                </p>

                {/* Grove City College Logo */}
                <img
                    src="https://edurank.org/assets/img/uni-logos/grove-city-college-logo.png"
                    alt="Grove City College Logo"
                    style={{ maxWidth: '150px', marginBottom: '2rem' }}
                />

                <div style={{
                    backgroundColor: 'white',
                    borderRadius: '16px',
                    padding: '20px',
                    textAlign: 'center',
                    boxShadow: '0px 4px 10px rgba(0, 0, 0, 0.1)',
                    maxWidth: '600px',
                    margin: 'auto'
                }}>
                    <h2 style={{ fontSize: '1.5rem', marginBottom: '1rem', color: '#990000' }}>
                        Your One-Stop App to create the perfect schedule!
                    </h2>
                    <p style={{ marginBottom: '1.5rem', fontSize: '1.1rem', color: '#333' }}>
                        See what other students have to say, and share your own experience with professors.
                        Whether you're planning your next semester or just want the inside scoop, we've got you covered!
                    </p>

                    <button
                        onClick={handleLogin}
                        style={{
                            backgroundColor: '#990000',
                            color: 'white',
                            padding: '10px 20px',
                            borderRadius: '8px',
                            fontSize: '1.1rem',
                            fontWeight: 'bold',
                            border: 'none',
                            cursor: 'pointer',
                            transition: 'background-color 0.3s ease',
                        }}
                        onMouseEnter={(e) => e.target.style.backgroundColor = '#cc0000'}
                        onMouseLeave={(e) => e.target.style.backgroundColor = '#990000'}
                    >
                        Get Started - Login
                    </button>
                </div>
            </div>
        </div>
    );
};

export default HomePage;
