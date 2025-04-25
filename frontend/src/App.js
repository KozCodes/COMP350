// File: frontend/src/App.js
import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Layout from "./components/Layout/Layout";
import LoginSignup from "./components/LoginPage/LoginPage";
import Schedule from "./components/Schedule/schedule";
import ProfessorRatingPage from "./components/ProfessorRater/ProfessorRatingPage";
import HomePage from "./components/HomePage/HomePage";
import SearchPage from './components/SearchPage/SearchPage';
import Home from './components/Home/Home';
import Results from './components/ResultsandDescriptionPages/ResultsandDescriptionPages';
import SchedulerPage from "./components/SchedulerPage/SchedulerPage";
import 'bootstrap/dist/css/bootstrap.min.css';

const App = () => {
  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginSignup />} />
          <Route path="/schedule/:scheduleId" element={<Schedule />} />
          <Route path="/auto-scheduler" element={<SchedulerPage />} />
          <Route path="/rate-professors" element={<ProfessorRatingPage />} />
          <Route path="/search" element={<SearchPage />} />
          <Route path="/home" element={<Home />} />
          <Route path="/results" element={<Results />} />
        </Routes>
      </Layout>
    </BrowserRouter>
  );
};

export default App;