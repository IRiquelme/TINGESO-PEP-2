/* eslint-disable jsx-a11y/anchor-is-valid */
import React from "react";
import NavbarComponent from "./others-components/NavbarComponent";
import "../styles/menu.css";
import { useNavigate } from "react-router-dom";
import AdministrationService from "../services/AdministrationService";

export default function MenuComponent() {
  const navigate = useNavigate();

  const handleClickStudentList = () => {
    navigate("/student-list");
  };

  const handleClickInstallmentsUpdate = () => {
    AdministrationService.updateInstallments().then((response) => {
      alert("Planilla de pagos actualizada");
    });
  };

  return (
    <div>
      <NavbarComponent />
      <body>
        <ul>
          <li>
            <a className="boton ingresar" href="/student-register">
              Ingresar estudiante
            </a>
          </li>
          <li>
            <a className="boton listar" onClick={handleClickStudentList}>
              Listar todos los estudiantes
            </a>
          </li>
          <li>
            <a className="boton archivo" href="/exam">
              Subir archivo de resultados
            </a>
          </li>
          <li>
            <a className="boton calcular" onClick={handleClickInstallmentsUpdate}>
              Calcular planilla de pagos
            </a>
          </li>
          <li>
            <a className="boton resumen" href="/summary">
              Generar reporte de estados de pago
            </a>
          </li>
        </ul>
      </body>
    </div>
  );
}
