import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./Login";
import Signup from "./Signup";

function App() {
  return (
    <Router>
      <Routes>
        {/* Ruta para el formulario de inicio de sesión */}
        <Route path="/" element={<Login />} />

        {/* Ruta para el formulario de registro */}
        <Route path="/signup" element={<Signup />} />
      </Routes>
    </Router>
  );
}

export default App;
