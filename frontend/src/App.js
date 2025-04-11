import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './components/LoginSignup/LoginSignup';

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
    <Router>
      <div className="App">
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          {/* Add other routes here as needed */}
        </Routes>
        <header className="App-header">
          <p>Data from backend: {data}</p>
        </header>
      </div>
    </Router>
  );
}