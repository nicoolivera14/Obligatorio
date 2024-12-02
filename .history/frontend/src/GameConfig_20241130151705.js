import React, { useState } from "react";
import "./GameConfig.css";

const GameConfig = () => {
  const [players, setPlayers] = useState(1);
  const [questions, setQuestions] = useState(1);const [message, setMessage] = useState("");

  const handlePlayersChange = (e) => {
    const value = Math.max(1, parseInt(e.target.value) || 1);
    setPlayers(value);
  };

  const handleQuestionsChange = (e) => {
    const value = Math.max(1, parseInt(e.target.value) || 1);
    setQuestions(value);
  };

  const handleSubmit = async () => {
    try {
      const response = await fetch("http://localhost:8080/game-config", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ players, questions }),
      });

      if (response.ok) {
        setMessage("Configuración enviada con éxito.");
      } else {
        setMessage("Error al enviar la configuración.");
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
            value={questions}
            min="1"
            onChange={handleQuestionsChange}
            className="config-input"
          />
        </div>
        <div className="config-input-group">
          <label className="config-label">Cantidad de jugadores</label>
          <input
            type="number"
            value={players}
            min="1"
            onChange={handlePlayersChange}
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
