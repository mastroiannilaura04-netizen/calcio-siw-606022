import React from "react";
import ReactDOM from "react-dom/client";
import { App } from "./App";

const rootElement = document.getElementById("root");
const squadre = JSON.parse(rootElement.dataset.squadre);

ReactDOM.createRoot(rootElement).render(
  <React.StrictMode>
    <App squadre={squadre} />
  </React.StrictMode>
);