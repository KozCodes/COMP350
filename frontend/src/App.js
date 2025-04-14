import logo from './logo.svg';
import './App.css';
import React, { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
  const [professors, setProfessors] = useState([]);
  const [message, setMessage] = useState('Loading professors...');

  useEffect(() => {
    axios.get('http://localhost:8080/faculty')
      .then(response => {
        if (response.data && response.data.length > 0) {
          setProfessors(response.data);
          setMessage('');
        } else {
          setMessage('No professors found.');
        }
      })
      .catch(error => {
        setMessage('There was an error fetching the professors.');
        console.error('There was an error!', error);
      });
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <h1>Faculty List</h1>
        {message && <p>{message}</p>} {/* Display loading/error messages */}
        {professors.length > 0 ? (
          <ul>
            {professors.map(professor => (
              <li key={professor.id}>
                <strong>{professor.name}</strong> - {professor.department}
              </li>
            ))}
          </ul>
        ) : (
          <p>No professors to display.</p>
        )}
      </header>
    </div>
  );
}

export default App;
