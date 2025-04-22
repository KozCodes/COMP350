// src/components/LoginSignup/LoginSignup.jsx
import React, { useState } from 'react';
import axios from 'axios';

const LoginSignup = () => {
  const [formType, setFormType] = useState('login'); // toggle between login/signup
  const [formData, setFormData] = useState({
    id: '',
    username: '',
    password: '',
    name: '',
    major: '',
    minor: ''
  });

  const handleChange = (e) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const endpoint = formType === 'login' ? '/login' : '/signup';
    const url = `http://localhost:8080/api${endpoint}`;

    try {
      const response = await axios.post(url, formData);
      alert(response.data); // Show success message
    } catch (error) {
      alert(error.response?.data || 'An error occurred.');
    }
  };

  return (
    <div>
      <h2>{formType === 'login' ? 'Login' : 'Sign Up'}</h2>
      <form onSubmit={handleSubmit}>
        <input name="username" placeholder="Username" onChange={handleChange} required />
        <input name="password" type="password" placeholder="Password" onChange={handleChange} required />

        {formType === 'signup' && (
          <>
            <input name="id" placeholder="Student ID" onChange={handleChange} required />
            <input name="name" placeholder="Full Name" onChange={handleChange} required />
            <input name="major" placeholder="Major" onChange={handleChange} required />
            <input name="minor" placeholder="Minor" onChange={handleChange} />
          </>
        )}

        <button type="submit">{formType === 'login' ? 'Login' : 'Sign Up'}</button>
      </form>

      <button onClick={() => setFormType(formType === 'login' ? 'signup' : 'login')}>
        Switch to {formType === 'login' ? 'Sign Up' : 'Login'}
      </button>
    </div>
  );
};

export default LoginSignup;
