import React, { useState } from "react";
import "./GameConfig.css";

const GameConfig = () => {
  const [cantJugadores, setJugadores] = useState(1);
  const [cantPreguntas, SetPreguntas] = useState(1);
  const [message, setMessage] = useState("");

  const handlePlayersChange = (e) => {
    const value = Math.max(1, parseInt(e.target.value) || 1);
    setJugadores(value);
  };

  const handleQuestionsChange = (e) => {
    const value = Math.max(1, parseInt(e.target.value) || 1);
    SetPreguntas(value);
  };

  const handleSubmit = async () => {
    try {
      const response = await fetch("http://localhost:8080/sala/crear/", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ jugadores, preguntas }),
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
            value={preguntas}
            min="1"
            onChange={handlePreguntasChange}
            className="config-input"
          />
        </div>
        <div className="config-input-group">
          <label className="config-label">Cantidad de jugadores</label>
          <input
            type="number"
            value={jugadores}
            min="1"
            onChange={handleJugadoresChange}
            className="config-input"
          />
        </div>
        <button className="config-button">Crear sala</button>
        <button className="config-button">Unirse a sala</button>
        <button className="config-button">Volver al menú</button>
      </div>
    </div>
  );
};

export default GameConfig;
