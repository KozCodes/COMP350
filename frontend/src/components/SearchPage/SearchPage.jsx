import React, {useState, useEffect} from 'react';
import axios from 'axios';

function SearchPage() {
    const [query, setQuery] = useState('');
    const [querylist, setQueryList] = useState([]);

    const  handleSearch = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.get(`http://localhost:8080/api/search/${query}`);
        } catch (error) {
          setMessage(error.response.data || 'We\'re sorry, we are unable to parse your request.');
        }
    }

    return(
    <div>
    <h2> Course Search </h2>
    <form onSubmit={handleSearch}>
    <input>
    <input name = "Search..."
    value = {query}
    onChange={(e) => setQuery(e.target.value)}
    required
    />
    </input>
    <button type= "submit"> Search </button>
    </form>
   </div>
    );
    }


export default SearchPage;