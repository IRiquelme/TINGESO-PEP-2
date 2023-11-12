/* eslint-disable jsx-a11y/anchor-is-valid */
import React from "react";
import NavbarComponent from "./others-components/NavbarComponent";
import { useNavigate } from "react-router-dom";
import AdministrationService from "../services/AdministrationService";
import "../styles/menu.css";

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

  const handleClickExam = () => {
    navigate("/exam-upload");
  };

  const handleClickReport = () => {
    navigate("/report-list");
  }
  return (
    <div>
      <NavbarComponent />
      <div className="menu">
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
            <a className="boton archivo" onClick={handleClickExam}>
              Subir resultados de examen
            </a>
          </li>
          <li>
            <a className="boton calcular" onClick={handleClickInstallmentsUpdate}>
              Calcular planilla de pagos
            </a>
          </li>
          <li>
            <a className="resumen boton" onClick={handleClickReport}>
              Generar reporte de estados de pago
            </a>
          </li>
        </ul>
      </div>
    </div>
  );
}
