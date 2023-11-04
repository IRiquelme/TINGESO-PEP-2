import React, { useState, useEffect } from "react";
import NavbarComponent from "./others-components/NavbarComponent";
import AdministrationService from "../services/AdministrationService";
import "../styles/list.css";

export default function ExamListComponent() {
  const [exams, setExams] = useState([]);

  useEffect(() => {
    AdministrationService.getLastExam().then((response) => {
      setExams(response.data);
    });
  }, []);

  return (
    <div className="main-container">
      <NavbarComponent />
      <div className="container">
        <table className="table">
          <thead className="thead-dark">
            <tr>
              <th>RUT</th>
              <th>Fecha</th>
              <th>Puntaje</th>
            </tr>
          </thead>
          <tbody>
            {exams.length > 0 ? (
              exams.map((examRow) => (
                <tr key={examRow.id}>
                  <td>{examRow.rut}</td>
                  <td>{examRow.date}</td>
                  <td>{examRow.score}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="3">No hay exámenes disponibles</td>
              </tr>
            )}
        </tbody>
      </table>
    </div>
  </div>
  );
}
