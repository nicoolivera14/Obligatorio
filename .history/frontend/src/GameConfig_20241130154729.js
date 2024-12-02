import React, { useState } from "react";
import "./GameConfig.css";

const GameConfig = () => {
  const [cantJugadores, setCantJugadores] = useState(1);
  const [cantPreguntas, SetCantPreguntas] = useState(1);
  const [message, setMessage] = useState("");

  const handleCantJugadoresChange = (e) => {
    const value = Math.max(1, parseInt(e.target.value) || 1);
    setCantJugadores(value);
  };

  const handleCantPreguntasChange = (e) => {
    const value = Math.max(1, parseInt(e.target.value) || 1);
    SetCantPreguntas(value);
  };

  const handleSubmit = async () => {
    try {
      const response = await fetch("http://localhost:8080/sala/crear/", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ cantJugadores, cantPreguntas }),
      });

      if (response.ok) {
        const data = await response.json(); // Obtén la respuesta del backend
        setMessage(`Sala creada con éxito: ID ${data.salaId}`);
      } else {
        setMessage("Error al crear la sala.");
      }
    } catch (error) {
      setMessage("No se pudo conectar con el servidor.");
    }
  };

  return (
    <div className="config-container">
      <div className="config-card">
        <h2 className="config-title">Configuración de partida</h2>
        <div className="config-input-group">
          <label className="config-label">Cantidad de preguntas</label>
          <input
            type="number"
            value={cantPreguntas}
            min="1"
            onChange={handleCantPreguntasChange}
            className="config-input"
          />
        </div>
        <div className="config-input-group">
          <label className="config-label">Cantidad de jugadores</label>
          <input
            type="number"
            value={cantJugadores}
            min="1"
            onChange={handleCantJugadoresChange}
            className="config-input"
          />
        </div>
        <button className="config-button" onc>Crear sala</button>
        <button className="config-button">Unirse a sala</button>
        <button className="config-button">Volver al menú</button>
      </div>
    </div>
  );
};

export default GameConfig;
