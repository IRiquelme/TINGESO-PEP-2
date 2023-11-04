/* eslint-disable no-unused-vars */
import React, { useState, useEffect } from "react";
import InstallmentService from "../services/InstallmentService";
import NavbarComponent from "./others-components/NavbarComponent";

export default function InstallmentListComponent() {
  const [installments, setInstallments] = useState([]);

  useEffect(() => {
    InstallmentService.getInstallmentsByRut(localStorage.getItem("rut")).then(
      (response) => {
        setInstallments(response.data);
      }
    );
  }, []);

  const payInstallment = (id) => {
    InstallmentService.payInstallment(id).then((response) => {
      window.location.reload();
    });
  }

  return (
    <div className="main-container">
      <NavbarComponent />
      <div className="container-sm">
        <h1>Cuotas</h1>
        <table className="table">
          <thead className="thead-dark">
            <tr>
              <th>Numero de Cuota</th>
              <th>Estado</th>
              <th>Fecha de Vencimiento</th>
              <th>Monto (CLP$)</th>
            </tr>
          </thead>
          <tbody>
            {installments.map((installment) => (
              <tr key={installment.id}>
                <td>{installment.installmentNumber}</td>
                <td>{installment.status}</td>
                <td>{installment.expirationDate}</td>
                <td>{installment.amount}</td>
                {installment.status === "PENDIENTE" &&
                  new Date() >= new Date(installment.startDate) && (
                    <td>
                      <button onClick={ () => payInstallment(installment.id)} className="btn btn-success">PAGAR</button>
                    </td>
                  )}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
