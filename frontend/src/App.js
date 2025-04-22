import React, {useEffect} from "react";
import axios from "axios";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoginSignup from "./LoginSignup";
//import Home from "./Home";
import Schedule from "./components/Schedule/schedule";

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
      </Routes>
    </BrowserRouter>
  );
}

export default App;
