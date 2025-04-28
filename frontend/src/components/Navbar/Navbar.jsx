// File: frontend/src/components/Navbar/Navbar.jsx
import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => {
  return (
    <nav style={navbarStyle}>
      <div style={logoStyle}>
        <Link to="/" style={linkStyle}>
          MyApp
        </Link>
      </div>
      <ul style={navListStyle}>
        <li style={navItemStyle}>
          <Link to="/home" style={linkStyle}>
            Home
          </Link>
        </li>
        <li style={navItemStyle}>
          <Link to="/search" style={linkStyle}>
            Search Courses
          </Link>
        </li>
        <li style={navItemStyle}>
          <Link to="/auto-scheduler" style={linkStyle}>
            Schedule Generator
          </Link>
        </li>
        <li style={navItemStyle}>
          <Link to="/rate-professors" style={linkStyle}>
            Professor Rater
          </Link>
        </li>
        <li style={navItemStyle}>
          <Link to="/login" style={linkStyle}>
            Logout
          </Link>
        </li>

      </ul>
    </nav>
  );
};

const navbarStyle = {
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center',
  padding: '1rem 2rem',
  backgroundColor: '#990000',
  color: 'white',
  position: 'sticky',
  top: 0,
  zIndex: 1000,
};

const logoStyle = {
  fontSize: '1.5rem',
  fontWeight: 'bold',
};

const navListStyle = {
  listStyle: 'none',
  display: 'flex',
  gap: '1.5rem',
  margin: 0,
  padding: 0,
};

const navItemStyle = {
  fontSize: '1rem',
};

const linkStyle = {
  color: 'white',
  textDecoration: 'none',
  fontWeight: 'bold',
  transition: 'color 0.3s',
};

export default Navbar;