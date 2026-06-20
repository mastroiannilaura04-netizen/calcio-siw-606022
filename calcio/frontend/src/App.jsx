import React, { useState } from "react";

export function App({ squadre }) {
  const [filtroCitta, setFiltroCitta] = useState("TUTTE");
  const [squadraApertaId, setSquadraApertaId] = useState(null);

  const cittaUniche = Array.from(
    new Set(squadre.map(s => s.citta).filter(Boolean))
  ).sort();

  const squadreFiltrate = squadre.filter(s =>
    filtroCitta === "TUTTE" || s.citta === filtroCitta
  );

  return (
    <div>
      <h2>Lista squadre React</h2>

      <label>Filtra per città: </label>
      <select value={filtroCitta} onChange={(e) => setFiltroCitta(e.target.value)}>
        <option value="TUTTE">Tutte le città</option>
        {cittaUniche.map(citta => (
          <option key={citta} value={citta}>{citta}</option>
        ))}
      </select>

      <ul>
        {squadreFiltrate.map(squadra => (
          <li key={squadra.id}>
            <strong onClick={() => setSquadraApertaId(
              squadraApertaId === squadra.id ? null : squadra.id
            )}>
              {squadra.nome}
            </strong>

            {squadraApertaId === squadra.id && (
              <div>
                <p>Città: {squadra.citta}</p>
                <p>Anno fondazione: {squadra.annoFondazione}</p>
              </div>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}