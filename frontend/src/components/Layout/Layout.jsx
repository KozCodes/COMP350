// File: frontend/src/components/Layout/Layout.jsx
import React from 'react';
import Navbar from '../Navbar/Navbar';

const Layout = ({ children }) => {
  return (
    <div>
      <Navbar />
      <div style={{ paddingTop: '0rem' }}>{children}</div>
    </div>
  );
};

export default Layout;