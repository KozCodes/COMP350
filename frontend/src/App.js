import logo from './logo.svg';
import './App.css';
import React, { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
  const [data, setData] = useState('');

  useEffect(() => {
    axios.get('http://localhost:8080/runFunction')
      .then(response => {
        setData(response.data);
      })
      .catch(error => {
        console.error('There was an error!', error);
      });
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <p>Data from backend: {data} </p>
      </header>
    </div>
  );
}



function Search(e) {
const [results, setResults] = useState('');

return (
<form method = "post" onSubmit = {} >
<input name = "SearchQuery"/>
</form>
);
}


export default App;
