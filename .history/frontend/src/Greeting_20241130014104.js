import React, { useEffect, useState } from "react";

const Greeting = () => {
  const [username, setUsername] = useState("");

  useEffect(() => {
    // Obtener el nombre de usuario desde localStorage o alguna otra fuente (por ejemplo, el estado global)
    const storedUsername = localStorage.getItem("username");
    if (storedUsername) {
      setUsername(storedUsername);
    }
  }, []);

  return (
    <div className="greeting-container">
      <h1>¡Hola, {username ? username : "Usuario!"}</h1>
    </div>
  );
};

export default Greeting;