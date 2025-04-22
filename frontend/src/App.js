import React, {useEffect} from "react";
import axios from "axios";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoginSignup from "./components/LoginPage/LoginPage";
import Schedule from "./components/Schedule/schedule";
import ProfessorRatingPage from "./components/ProfessorRater/ProfessorRatingPage";
import HomePage from "./components/HomePage/HomePage";
import SearchPage from './components/SearchPage/SearchPage';


function Home() {
    return(
        <div>
            <h1>Welcome to the Home Page</h1>
        </div>
    );
}

const App = () => {
//
//    const load = async () => {
//        try {
//            await axios.get(`http://localhost:8080/api/test`);
//        } catch (error) {
//            console.error("Error fetching schedule data:", error);
//        }
//    };
//
//    useEffect(() => {
//        load();
//    }, []);

  return (
    <BrowserRouter>
  <Routes>
        <Route
          path="/"
          element={<HomePage />}
        />
        <Route
          path="/login"
          element={<LoginSignup />}
        />
        <Route
          path="/schedule/:scheduleId"
          element={<Schedule />}
        />
        <Route
          path="/rate-professors"
          element={<ProfessorRatingPage />}
        />
         <Route path="/search" element={<SearchPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
