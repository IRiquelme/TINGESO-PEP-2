import React, { useState, useEffect } from "react";
import StudentService from "../services/StudentService";
import NavbarComponent from "./others-components/NavbarComponent";
import { useNavigate } from "react-router-dom";
import "../styles/list.css";

export default function StudentListComponent() {
  const [students, setStudents] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    StudentService.getStudents().then((response) => {
      setStudents(response.data);
    });
  }, []);

  const listInstallments = (rut) => {
    localStorage.setItem("rut", rut);
    navigate("/installment-list");
  };

  return (
    <div className="main-container">
      <NavbarComponent />
      <div className="container-sm">
        <table className="table">
          <thead className="thead-dark">
            <tr>
              <th>RUT</th>
              <th>Apellidos</th>
              <th>Nombres</th>
              <th>Fecha de Nacimiento</th>
              <th>Tipo de Colegio</th>
              <th>Nombre del Colegio</th>
              <th>Años de Egreso</th>
            </tr>
          </thead>
          <tbody>
            {students.map((student) => (
              <tr key={student.rut}>
                <td>{student.rut}</td>
                <td>{student.lastNames}</td>
                <td>{student.names}</td>
                <td>{student.dateBirth}</td>
                <td>{student.schoolType}</td>
                <td>{student.schoolName}</td>
                <td>{student.yearsSinceGraduation}</td>
                <td>
                  <button onClick={() => listInstallments(student.rut)} className="btn btn-primary">
                    CUOTAS
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}