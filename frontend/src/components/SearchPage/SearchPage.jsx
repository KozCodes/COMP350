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
      <div style={{textAlign: 'left', padding: '2rem'}}>
   <button style = {{fontWeight: 'bold', backgroundColor: '#990000', color: 'white'}} onClick={() => navigate('/Home')}> Home
   </button>
   <button style = {{fontWeight: 'bold', backgroundColor: '#990000', color: 'white'}} onClick={() => navigate('/Home')}> See Schedule
      </button>
   <div style={{textAlign: 'center', padding: '4rem', minHeight: '100vh', color: 'black' }}>
   <h2 style = {{ fontWeight: 'bold', color: '#990000'}}> Course Search </h2>
   <div style= {{textAlign: 'center', padding: '2rem'}}>
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
   <div style={{textAlign: 'center', padding: '2rem', minHeight: '100vh', color: 'black' }}>
   <h1> Filter Your Search </h1>
   <div style={{display: 'flex', flexDirection: 'row', justifyContent: 'space-evenly'}}>
    <label> Filter by Days </label>
    
   <label> Filter by Times </label>
    <label> Filter by Session </label>
    <label> Filter by Year </label>
    <label> Filter by Course Code </label>
    <label> Filter by Course Department </label>
    <label> Filter by Course Year </label>
    </div>
   </div>
   </div>
   </div>
   </div>
  );
};

export default CourseSearch;
