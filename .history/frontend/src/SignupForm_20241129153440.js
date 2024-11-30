import React, { useState } from "react";
import "./Signup.css";

const SignupForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    if (username.length < 3) {
        setError("Username debe tener más de 3 carácteres.");
        setSuccess("");
        return;
    }

    if (password !== confirmPassword) {
      setError("Passwords no coinciden.");
      setSuccess("");
      return;
    }

    if (password.length < 8) {
      setError("Password debe tener más de 8 carácteres.");
      setSuccess("");
      return;
    }

    // Simulación de registro (llamar a tu backend aquí)
    fetch("http://localhost:8080/signup", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password }),
    })
      .then((response) => {
        if (response.ok) {
          setError("");
          setSuccess("Usuario registrado correctamente!");
          setUsername("");
          setPassword("");
          setConfirmPassword("");
        } else if (response.status === 409) {
          setError("Username ya existe.");
          setSuccess("");
        } else {
          setError("An unexpected error occurred.");
          setSuccess("");
        }
      })
      .catch(() => {
        setError("Failed to connect to the server.");
        setSuccess("");
      });
  };

  return (
    <div className="signup-container">
      <h1>Sign Up</h1>
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
        <div className="input-container">
          <input
            type="password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            placeholder="Confirm Password"
            required
          />
        </div>
        {error && <p className="error-message">{error}</p>}
        {success && <p className="success-message">{success}</p>}
        <button type="submit">Sign Up</button>
      </form>
      <p className="login-link">
        Already have an account? <a href="/login">Log in.</a>
      </p>
    </div>
  );
};

export default SignupForm;
