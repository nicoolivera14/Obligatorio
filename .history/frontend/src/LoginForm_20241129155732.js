import React, { useState } from "react";
import "./Login.css";

const LoginForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    // Aquí puedes agregar lógica para verificar el login
    if (username === "admin" && password === "1234") {
      setError("");
      alert("Inicio de sesión exitoso");
    } else {
      setError("Incorrect password.");
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

export default Login;
