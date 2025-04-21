import React, { useEffect, useState } from 'react';
import axios from 'axios';
axios.defaults.withCredentials = true;

const ProfessorRatingPage = () => {
  const [professors, setProfessors] = useState([]);
  const [search, setSearch] = useState('');
  const [ratings, setRatings] = useState({});

  useEffect(() => {
    axios.get('http://localhost:8080/api/professors')
      .then(res => setProfessors(res.data))
      .catch(err => console.error('Error loading professors:', err));
  }, []);

  const handleSearchChange = (e) => setSearch(e.target.value);

  const handleRatingChange = (professorId, value) => {
    setRatings(prev => ({ ...prev, [professorId]: parseInt(value) }));
  };

  const submitRating = async (professorId) => {
    const rating = ratings[professorId];

    if (rating === undefined || isNaN(rating) || rating < 0 || rating > 5) {
      alert("Please select a valid rating between 0 and 5.");
      return;
    }

    try {
      const res = await axios.put(
        `http://localhost:8080/api/professor/${professorId}/rating`,
        { rating }
      );
      alert(res.data);

      const refreshed = await axios.get('http://localhost:8080/api/professors');
      setProfessors(refreshed.data);
    } catch (error) {
      alert(error.response?.data || 'Failed to rate professor.');
    }
  };

  const filteredProfessors = professors.filter(p =>
    p.name.toLowerCase().includes(search.toLowerCase()) ||
    p.department.toLowerCase().includes(search.toLowerCase())
  );

  const renderStars = (score) => {
    const fullStars = Math.floor(score);
    const hasHalf = score % 1 >= 0.5;
    const emptyStars = 5 - fullStars - (hasHalf ? 1 : 0);

    return (
      <div style={{ fontSize: '1.2rem', color: '#FFD700' }}>
        {'★'.repeat(fullStars)}{hasHalf ? '½' : ''}{'☆'.repeat(emptyStars)}
      </div>
    );
  };

  return (
    <div style={{
      backgroundColor: '#f9f9f9',
      minHeight: '100vh',
      padding: '2rem',
      fontFamily: 'Segoe UI, sans-serif',
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center'
    }}>
      <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
        <h1 style={{ fontSize: '2.5rem', fontWeight: 'bold', color: '#990000' }}>
          GCC Professor Rater
        </h1>
        <p style={{ fontSize: '1.2rem', color: '#444' }}>
          Search for your professors and rate them based on your experience!
        </p>
      </div>

      <input
        type="text"
        placeholder="Search by name or department..."
        value={search}
        onChange={handleSearchChange}
        style={{
          width: '100%',
          maxWidth: '500px',
          padding: '0.75rem',
          fontSize: '1rem',
          borderRadius: '8px',
          border: '1px solid #ccc',
          marginBottom: '2rem',
          backgroundColor: 'white'
        }}
      />

      <div style={{ width: '100%', maxWidth: '700px' }}>
        {filteredProfessors.map(prof => (
          <div key={prof.id} style={{
            backgroundColor: '#fff',
            borderRadius: '12px',
            padding: '1.5rem',
            marginBottom: '1.5rem',
            boxShadow: '0px 4px 12px rgba(0,0,0,0.05)'
          }}>
            <h2 style={{ marginBottom: '0.3rem', color: '#222' }}>{prof.name}</h2>
            <p style={{ margin: '0.25rem 0' }}>
              <strong>Department:</strong> {prof.department}
            </p>
            <p style={{ margin: '0.25rem 0' }}>
              <strong>Average Rating:</strong>{' '}
              {prof.score !== null && prof.score !== undefined
                ? `${prof.score.toFixed(2)}`
                : 'No ratings yet'}
            </p>
            {prof.score !== null && renderStars(prof.score)}

            <div style={{ marginTop: '1rem', display: 'flex', alignItems: 'center' }}>
              <label style={{ marginRight: '0.5rem' }}>
                Your Rating:
              </label>
              <select
                value={ratings[prof.id] ?? ''}
                onChange={(e) => handleRatingChange(prof.id, e.target.value)}
                style={{
                  padding: '0.4rem',
                  borderRadius: '6px',
                  border: '1px solid #ccc'
                }}
              >
                <option value="">Select</option>
                {[0, 1, 2, 3, 4, 5].map(num => (
                  <option key={num} value={num}>{num}</option>
                ))}
              </select>

              <button
                onClick={() => submitRating(prof.id)}
                style={{
                  marginLeft: '1rem',
                  backgroundColor: '#990000',
                  color: 'white',
                  border: 'none',
                  padding: '0.5rem 1rem',
                  borderRadius: '6px',
                  cursor: 'pointer',
                  fontWeight: 'bold'
                }}
              >
                Submit Rating
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProfessorRatingPage;
