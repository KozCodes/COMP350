import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoginSignup from "./LoginSignup";

function Home() {
  return (
   <div>
      <h1>Welcome to the Blog</h1>
      <p>This is the home page.</p>
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
      </Routes>
    </BrowserRouter>
  );
}

export default App;
