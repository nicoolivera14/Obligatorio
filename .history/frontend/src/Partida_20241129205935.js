import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Partida.css";

const Partida = ({ idSala, idUsuario }) => {
  const [pregunta, setPregunta] = useState(null);
  const [selectedOption, setSelectedOption] = useState(null);
  const [isAnswered, setIsAnswered] = useState(false);
  const [mensajeRespuesta, setMensajeRespuesta] = useState("");

  // Obtener la siguiente pregunta
  const fetchPregunta = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/${idSala}/siguiente-pregunta`,
        { params: { idUsuario } }
      );
      setPregunta(response.data);
      setSelectedOption(null);
      setIsAnswered(false);
      setMensajeRespuesta("");
    } catch (error) {
      if (error.response && error.response.status === 204) {
        setMensajeRespuesta("No hay más preguntas disponibles.");
      } else if (error.response && error.response.status === 403) {
        setMensajeRespuesta("Solo el host puede solicitar la siguiente pregunta.");
      } else {
        setMensajeRespuesta("Error al obtener la pregunta.");
      }
    }
  };

  // Enviar la respuesta del usuario
  const responderPregunta = async (idPregunta, respuestaUsuario) => {
    try {
      const response = await axios.post(
        `http://localhost:8080/${idSala}/responder-pregunta`,
        null,
        {
          params: {
            idPregunta,
            respuestaUsuario,
          },
        }
      );
      setMensajeRespuesta(response.data);
    } catch (error) {
      setMensajeRespuesta("Error al enviar la respuesta.");
    }
  };

  const handleOptionClick = (opcion, index) => {
    if (!isAnswered) {
      setSelectedOption(index);
      setIsAnswered(true);
      responderPregunta(pregunta.idPregunta, index);
    }
  };

  const getOptionClass = (index) => {
    if (!isAnswered) return "";
    if (index === pregunta.respuestaCorrecta) return "correct";
    if (index === selectedOption) return "incorrect";
    return "incorrect";
  };

  useEffect(() => {
    fetchPregunta();
  }, []);

  return (
    <div className="game-container">
      {/* Mensaje de respuesta */}
      {mensajeRespuesta && <div className="mensaje-respuesta">{mensajeRespuesta}</div>}

      {/* Mostrar pregunta y opciones */}
      {pregunta && (
        <>
          <div className="question">{pregunta.texto}</div>
          <div className="options-container">
            {pregunta.respuestas.map((opcion, index) => (
              <button
                key={index}
                className={`option ${getOptionClass(index)}`}
                onClick={() => handleOptionClick(opcion, index)}
                disabled={isAnswered}
              >
                {opcion}
              </button>
            ))}
          </div>

          {/* Botón para la siguiente pregunta */}
          {isAnswered && (
            <button className="next-question" onClick={fetchPregunta}>
              Siguiente pregunta
            </button>
          )}
        </>
      )}
    </div>
  );
};

export default Partida;
