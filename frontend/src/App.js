import React from "react";
import axios from "axios";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoginSignup from "./LoginSignup";
//import Home from "./Home";
import Schedule from "./components/Schedule/schedule";
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
          element={<Home />}
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
      </Routes>
    </BrowserRouter>
  );
}

export default App;
