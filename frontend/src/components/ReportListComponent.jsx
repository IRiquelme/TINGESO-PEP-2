import React, { useState, useEffect } from "react";
import AdministrationService from "../services/AdministrationService";
import NavbarComponent from "./others-components/NavbarComponent";
import "../styles/list.css";

export default function ReportListComponent() {
  const [report, setReport] = useState([]);
  useEffect(() => {
    AdministrationService.createReport().then((response) => {
      setReport(response.data);
    });
  }, []);

  return (
    <div className="main-container">
      <NavbarComponent />
      <div className="container-sm">
        <table className="table">
          <thead className="thead-dark">
            <tr>
              <th>RUT</th>
              <th>Nombre</th>
              <th>Nro. exámenes rendidos</th>
              <th>Promedio puntaje exámenes</th>
              <th>Monto total arancel a pagar (CLP$)</th>
              <th>Tipo Pago</th>
              <th>Nro. total de cuotas pactadas</th>
              <th>Nro. cuotas pagadas</th>
              <th>Monto total pagado (CLP$)</th>
              <th>Fecha último pago</th>
              <th>Saldo por pagar (CLP$)</th>
              <th>Nro. Cuotas con retraso</th>
            </tr>
          </thead>
          <tbody>
            {report.map((reportRow) => (
              <tr key={reportRow.id}>
                <td>{reportRow.rut}</td>
                <td className="no-center">{reportRow.studentName}</td>
                <td>{reportRow.examsCount}</td>
                <td>{reportRow.averageScore}</td>
                <td>{reportRow.totalAmount}</td>
                <td>{reportRow.paymentType}</td>
                <td>{reportRow.numberOfInstallments}</td>
                <td>{reportRow.numberOfPaidInstallments}</td>
                <td>{reportRow.totalPaidAmount}</td>
                <td>{reportRow.lastPaymentDate}</td>
                <td>{reportRow.remainingAmount}</td>
                <td>{reportRow.numberOfLateInstallments}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
