import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const LoginPage = () => {
  const [isLogin, setIsLogin] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const logout = async () => {
      try {
        await axios.post('http://localhost:8080/api/logout', {}, { withCredentials: true });
      } catch (error) {
        console.error('Logout failed:', error);
      }
    };

    logout();

    // Prevent back navigation
    window.history.pushState(null, '', window.location.href);
    const handlePopState = () => window.history.go(1);
    window.addEventListener('popstate', handlePopState);

    // Optional: clear localStorage
    localStorage.clear();

    return () => {
      window.removeEventListener('popstate', handlePopState);
    };
  }, []);

  const [formData, setFormData] = useState({
    id: '',
    username: '',
    password: '',
    name: '',
    major: '',
    minor: ''
  });

  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const endpoint = isLogin ? '/login' : '/signup';
    const url = `http://localhost:8080/api${endpoint}`;

    try {
      const response = await axios.post(url, formData, { withCredentials: true });
      setMessage(response.data);

      if (isLogin) {
        navigate('/home');
      } else {
        window.location.reload(); // Force page reload after signup
      }
    } catch (error) {
      setMessage(error.response?.data || 'An error occurred.');
    }
  };

  return (
    <div style={{
      backgroundColor: '#f4f4f4',
      minHeight: '100vh',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      flexDirection: 'column',
      padding: '2rem'
    }}>
      <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
        <img
          src="https://www.edarabia.com/wp-content/uploads/2013/08/grove-city-college-logo-usa.jpg"
          alt="Grove City College Logo"
          style={{ maxWidth: '200px', marginBottom: '1.5rem' }}
        />
        <h1 style={{ fontSize: '2.5rem', fontWeight: 'bold', color: '#990000', marginBottom: '1rem' }}>Welcome to Grove Rate</h1>
        <p style={{ fontSize: '1.25rem', color: '#333', marginBottom: '2rem' }}>
          {isLogin ? 'Sign in to your account' : 'Create your student account'}
        </p>
      </div>

      <div style={{
        backgroundColor: 'white',
        borderRadius: '16px',
        padding: '30px',
        width: '100%',
        maxWidth: '450px',
        boxShadow: '0px 4px 10px rgba(0, 0, 0, 0.1)'
      }}>
        <form onSubmit={handleSubmit} autoComplete="off">
          {!isLogin && (
            <>
              <div style={{ marginBottom: '1rem' }}>
                <label htmlFor="id" style={{ fontWeight: 'bold' }}>Student ID</label>
                <input
                  type="text"
                  name="id"
                  onChange={handleChange}
                  autoComplete="off"
                  required
                  style={inputStyle}
                />
              </div>

              <div style={{ marginBottom: '1rem' }}>
                <label htmlFor="name" style={{ fontWeight: 'bold' }}>Full Name</label>
                <input
                  type="text"
                  name="name"
                  onChange={handleChange}
                  autoComplete="off"
                  required
                  style={inputStyle}
                />
              </div>

              <div style={{ marginBottom: '1rem' }}>
                <label htmlFor="major" style={{ fontWeight: 'bold' }}>Major</label>
                <input
                  type="text"
                  name="major"
                  onChange={handleChange}
                  autoComplete="off"
                  required
                  style={inputStyle}
                />
              </div>

              <div style={{ marginBottom: '1rem' }}>
                <label htmlFor="minor" style={{ fontWeight: 'bold' }}>Minor (optional)</label>
                <input
                  type="text"
                  name="minor"
                  onChange={handleChange}
                  autoComplete="off"
                  style={inputStyle}
                />
              </div>
            </>
          )}

          <div style={{ marginBottom: '1rem' }}>
            <label htmlFor="username" style={{ fontWeight: 'bold' }}>Username</label>
            <input
              type="text"
              name="username"
              onChange={handleChange}
              autoComplete="off"
              required
              style={inputStyle}
            />
          </div>

          <div style={{ marginBottom: '1.5rem' }}>
            <label htmlFor="password" style={{ fontWeight: 'bold' }}>Password</label>
            <input
              type="password"
              name="password"
              onChange={handleChange}
              autoComplete="new-password"
              required
              style={inputStyle}
            />
          </div>

          <button type="submit" style={buttonStyle}>
            {isLogin ? 'Login' : 'Sign Up'}
          </button>
        </form>

        {message && <p style={{ color: '#990000', marginTop: '1rem', textAlign: 'center' }}>{message}</p>}

        <div style={{ textAlign: 'center', marginTop: '1rem' }}>
          <p style={{ fontSize: '1rem', color: '#333' }}>
            {isLogin ? "Don't have an account?" : 'Already registered?'}{' '}
            <button
              onClick={() => {
                setFormData({
                  id: '',
                  username: '',
                  password: '',
                  name: '',
                  major: '',
                  minor: ''
                });
                setIsLogin(!isLogin);
                setMessage('');
              }}
              style={{ color: '#990000', border: 'none', background: 'none', cursor: 'pointer', fontWeight: 'bold' }}
            >
              {isLogin ? 'Sign Up' : 'Login'}
            </button>
          </p>
        </div>
      </div>
    </div>
  );
};

const inputStyle = {
  width: '100%',
  padding: '0.8rem',
  marginTop: '0.5rem',
  borderRadius: '8px',
  border: '1px solid #ccc',
  fontSize: '1rem',
  color: '#333'
};

const buttonStyle = {
  backgroundColor: '#990000',
  color: 'white',
  padding: '10px 20px',
  borderRadius: '8px',
  fontSize: '1.1rem',
  fontWeight: 'bold',
  border: 'none',
  cursor: 'pointer',
  width: '100%',
  transition: 'background-color 0.3s ease'
};

export default LoginPage;
