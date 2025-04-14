import logo from './logo.svg';
import './App.css';
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {BrowserRouter as Router, Route, Link, Routes} from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

function App() {

    const navigate = useNavigate();


   const handleClick = () => {
   navigate('/search');
   }

  return (
   <div>
    <h1> Welcome to the GCC Scheduling App! </h1>
    <button onClick = {handleClick}> Search </button>
    </div>
  );
}

function SearchPage(e) {
const [results, setResults] = useState('');

return (
<form method = "post" onSubmit = {} >
<input name = "SearchQuery"/>
</form>
);
}


export default App;
