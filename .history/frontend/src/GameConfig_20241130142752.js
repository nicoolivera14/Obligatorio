import React, { useState } from "react";

const GameConfig = () => {
  // Estados para la cantidad de jugadores y preguntas
  const [players, setPlayers] = useState(1);
  const [questions, setQuestions] = useState(1);

  // Funciones para manejar los cambios
  const handlePlayersChange = (e) => {
    const value = Math.max(1, parseInt(e.target.value) || 1);
    setPlayers(value);
  };

  const handleQuestionsChange = (e) => {
    const value = Math.max(1, parseInt(e.target.value) || 1);
    setQuestions(value);
  };

  return (
    <div style={styles.container}>
      <h1>Greetings</h1>
      <div style={styles.card}>
        <h2 style={styles.title}>Configuración de partida</h2>
        <div style={styles.inputGroup}>
          <label style={styles.label}>Cantidad de preguntas</label>
          <input
            type="number"
            value={questions}
            min="1"
            onChange={handleQuestionsChange}
            style={styles.input}
          />
        </div>
        <div style={styles.inputGroup}>
          <label style={styles.label}>Cantidad de jugadores</label>
          <input
            type="number"
            value={players}
            min="1"
            onChange={handlePlayersChange}
            style={styles.input}
          />
        </div>
        <button style={styles.button}>Crear sala</button>
        <button style={styles.button}>Unirse a sala</button>
        <button style={styles.button}>Volver al menú</button>
      </div>
    </div>
  );
};