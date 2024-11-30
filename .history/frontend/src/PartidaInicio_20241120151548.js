import React, { useState, useEffect } from 'react';

const PartidaInicio = () => {
  const [partida, setPartida] = useState(null);

  useEffect(() => {
    // Realizar la solicitud GET al backend
    fetch('http://localhost:8080/partida/iniciar')
  .then(response => response.json())
  .then(data => {
    console.log(data); // Partida iniciada correctamente
  })
  .catch(error => console.error('Error:', error));

  }, []);

  if (!partida) {
    return <div>Cargando partida...</div>;
  }

  return (
    <div>
      <h1>{partida.mensaje}</h1>
      <h2>Puntaje: {partida.puntaje}</h2>
      <h3>Preguntas:</h3>
      <ul>
        {partida.preguntas.map((pregunta, index) => (
          <li key={index}>{pregunta}</li>
        ))}
      </ul>
    </div>
  );
};

export default PartidaInicio;
