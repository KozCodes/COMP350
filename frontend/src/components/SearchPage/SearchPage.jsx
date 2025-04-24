import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';


const CourseSearch = () => {

    const [query, setQuery] = useState('');
    const navigate = useNavigate();

    const [querylist, setQueryList] = useState([]);

      const [message, setMessage] = useState('');

   const handleSearch = async (e) => {
           try {
               setMessage("We are currently searching for your query...");
               const response = await axios.get(`http://localhost:8080/api/search/${query}`);
               navigate('/results', {state: response.data});
           } catch (error) {
             setMessage(error || 'We\'re sorry, we are unable to parse your request.');
           }
  }

  return (
   <div style={{textAlign: 'center', padding: '2rem', minHeight: '100vh', color: 'black' }}>
   <h2 style = {{ fontWeight: 'bold', color: '#990000'}}> Course Search </h2>
   <input style = {{}}
   name = "Search..."
   value = {query}
   onChange={(e) => setQuery(e.target.value)}
   required
   />
   <button type= "submit"
        onClick={() => handleSearch()}> Search
    </button>
   <h1> {message}</h1>
  </div>
  );
};

export default CourseSearch;
