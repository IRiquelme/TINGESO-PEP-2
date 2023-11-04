import React, { useState } from "react";
import NavbarComponent from "./others-components/NavbarComponent";
import StudentService from "../services/StudentService";
import { useNavigate } from "react-router-dom";
import { validateRut } from "@fdograph/rut-utilities";
import "../styles/student-register.css";

const StudentRegistrationForm = () => {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        rut: "",
        lastNames: "",
        names: "",
        dateBirth: "",
        schoolType: "Municipal",
        schoolName: "",
        yearsSinceGraduation: "0",
    });

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validateRut(formData.rut)) {
            alert("RUT inválido");
            return;
        }
        try {
            let response = await StudentService.createStudent(formData);
            if (response.data === -1) {
                alert("El RUT ingresado ya existe");
                return;
            }
            localStorage.setItem("rut", formData.rut);
            localStorage.setItem("schoolType", formData.schoolType);
            navigate("/installment-generate");
        } catch (error) {
            console.error("Error al crear el estudiante: ", error);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    return (
        <div className="main-container">
            <NavbarComponent />
            <div className="form-container mb-3 div">
                <form onSubmit={handleSubmit}>
                    <h1>REGISTRAR ESTUDIANTE</h1>
                    <div className="form-group">
                        <label htmlFor="rut">RUT</label>
                        <input
                            className="form-control"
                            id="rut"
                            name="rut"
                            placeholder="Ingrese RUT (formato: xxxxxxxx-x)"
                            required
                            type="text"
                            value={formData.rut}
                            onChange={handleChange}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="lastNames">Apellidos</label>
                        <input
                            className="form-control"
                            id="lastNames"
                            name="lastNames"
                            placeholder="Ingrese apellidos"
                            required
                            type="text"
                            value={formData.lastNames}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="names">Nombres</label>
                        <input
                            className="form-control"
                            id="names"
                            name="names"
                            placeholder="Ingrese nombres"
                            required
                            type="text"
                            value={formData.names}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="dateBirth">Fecha Nacimiento</label>
                        <input
                            className="form-control"
                            id="dateBirth"
                            name="dateBirth"
                            min="1950-01-01"
                            max="2023-01-01"
                            required
                            type="date"
                            value={formData.dateBirth}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="schoolType">Tipo Colegio</label>
                        <select
                            className="form-control"
                            id="schoolType"
                            name="schoolType"
                            required
                            value={formData.schoolType}
                            onChange={handleChange}
                        >
                            <option>Municipal</option>
                            <option>Subvencionado</option>
                            <option>Privado</option>
                        </select>
                    </div>

                    <div className="form-group">
                        <label htmlFor="schoolName">Nombre Colegio</label>
                        <input
                            className="form-control"
                            id="schoolName"
                            name="schoolName"
                            placeholder="Ingrese colegio"
                            required
                            type="text"
                            value={formData.schoolName}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="yearsSinceGraduation">Años desde el egreso</label>
                        <select
                            className="form-control"
                            id="yearsSinceGraduation"
                            name="yearsSinceGraduation"
                            required
                            value={formData.yearsSinceGraduation}
                            onChange={handleChange}
                        >
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="4+">4+</option>
                        </select>
                    </div>

                    <button className="btn btn-primary" type="submit">
                        Registrar
                    </button>
                </form>
            </div>
        </div>
    );
};

export default StudentRegistrationForm;
