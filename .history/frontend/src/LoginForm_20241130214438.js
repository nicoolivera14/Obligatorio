import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./LoginForm.css";

const LoginForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Enviar credenciales al backend
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: ({ username, password }),
      });

      if (response.ok) {
        // Si el login es exitoso
        const data = await response.json(); // Si necesitas datos adicionales del backend
        setError("");
        alert("Inicio de sesión exitoso");

        localStorage.setItem("username", username);
        navigate("/greeting");
      } else if (response.status === 401) {
        // Si las credenciales son incorrectas
        setError("Usuario o contraseña incorrectos.");
      } else {
        // Otros errores
        setError("Error inesperado. Inténtalo más tarde.");
      }
    } catch (error) {
      console.error("Error:", error);
      setError("No se pudo conectar al servidor.");
    }
  };

  return (
    <div className="login-container">
      <h1>Log In</h1>
      <form onSubmit={handleSubmit}>
        <div className="input-container">
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="Username"
            required
          />
        </div>
        <div className="input-container">
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="Password"
            required
          />
        </div>
        {error && <p className="error-message">{error}</p>}
        <button type="submit">Log in</button>
      </form>
      <p className="signup-link">
        No estás registrado? <a href="/signup">Sign up.</a>
      </p>
    </div>
  );
};

export default LoginForm;
