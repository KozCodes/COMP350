import React from "react";
import axios from "axios";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoginSignup from "./components/LoginPage/LoginPage";
import Schedule from "./components/Schedule/schedule";
import ProfessorRatingPage from "./components/ProfessorRater/ProfessorRatingPage";
import HomePage from "./components/HomePage/HomePage";
import SearchPage from './components/SearchPage/SearchPage';
import SchedulerPage from "./components/SchedulerPage/SchedulerPage"; // npm install @dnd-kit/core @dnd-kit/sortable @dnd-kit/utilities
import 'bootstrap/dist/css/bootstrap.min.css'; // npm install bootstrap

function Home() {
    const load = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/test`);
            console.log(response.data);
        } catch (error) {
            console.error("Error fetching schedule data:", error);
        }
    };

    load();

    return(
        <div>
            <h1>Welcome to the Home Page</h1>
        </div>
    );
}

const App = () => {
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
        path="/auto-scheduler"
        element={<SchedulerPage />} />
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
