/* eslint-disable jsx-a11y/anchor-is-valid */
import React, { useState} from "react";
import { useNavigate } from "react-router-dom";
import AdministrationService from "../services/AdministrationService";
import NavbarComponent from "./others-components/NavbarComponent";

export default function ExamUploadComponent() {

  const navigate = useNavigate();
  const [file, setFile] = useState(null);

  const handleOnChange = (e) => {
    e.preventDefault();
    setFile(e.target.files[0]);
  }

  const handleExamUpload = async (e) => {
    e.preventDefault();
    try {
      const formData = new FormData();
      formData.append("exam", file);
      console.log(formData);
      let response = await AdministrationService.uploadExam(formData);
      if (response.data === -1) {
        alert("El archivo ingresado no es válido");
        return;
      }
      navigate("/exam-list");
    } catch (error) {
      alert("Verifique el archivo ingresado");
    }
  };  

  const handleExamList = (e) => {
    e.preventDefault();
    navigate("/exam-list");
  };

  return (
    <div className="main-container">
    <NavbarComponent />
      <div className="form-container mb-3 div">
        <form action="/exam-upload" encType="multipart/form-data" method="post">
          <div className="form-group">
            <input accept=".csv" name="exam" type="file" onChange={(e) => handleOnChange(e)}/>
          </div>
          <button className="boton" onClick={handleExamUpload}>
            Subir archivo
          </button>
          <br />
          <button className="boton" onClick={handleExamList}>
            Ver últimos datos subidos
          </button>
        </form>
        <br />
      </div>
    </div>
  )
}