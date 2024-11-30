import React, { useEffect, useState } from 'react';
import { fetchGreeting } from './api';

function App() {
    const [greeting, setGreeting] = useState('');

    useEffect(() => {
        fetchGreeting()
            .then(response => setGreeting(response.data))
            .catch(error => console.error("Error al obtener el saludo:", error));
    }, []);

    return (
        <div>
            <h1>{greeting}</h1>
        </div>
    );
}

export default App;
