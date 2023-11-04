import React, { useState, useEffect } from 'react';
import InstallmentService from '../services/InstallmentService';
import { useNavigate } from "react-router-dom";
import '../styles/installment-generate.css';


function InstallmentGenerateForm({ handleGenerate }) {

    const navigate = useNavigate();
    const [rut,] = useState(localStorage.getItem('rut'));
    const [options, setOptions] = useState([]);

    useEffect(() => {
        const schoolType = localStorage.getItem('schoolType');
        const generatedOptions = generateOptionsBasedOnSchoolType(schoolType);
        setOptions(generatedOptions);
    }, []);

    function generateOptionsBasedOnSchoolType(schoolType) {
        console.log(schoolType);
        let options = [];
        switch (schoolType) {
            case 'Municipal':
                options = ['2', '3', '4', '5', '6', '7', '8', '9', '10'];
                break;
            case 'Subvencionado':
                options = ['2', '3', '4', '5', '6', '7'];
                break;
            default:
                options = ['2', '3', '4'];
                break;
        }
        return options;
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await InstallmentService.createInstallments(rut, e.target.numberOfInstallments.value);
            localStorage.removeItem('rut');
            localStorage.removeItem('schoolType');
            navigate('/');
        } catch (error) {
            console.error('Error al generar cuotas: ', error);
        }
    };

    return (
        <div className="form-container mb-3 div">
            <form onSubmit={handleSubmit}>
                <h1>GENERAR CUOTAS</h1>

                <div className="form-group">
                    <label htmlFor="rut">RUT</label>
                    <input className="rut" id="rut" name="rut" readOnly value={rut} />
                </div>

                <div className="form-group">
                    <label htmlFor="numberOfInstallments">Numero de cuotas</label>
                    <select className="form-select" id="numberOfInstallments" name="numberOfInstallments">
                        <option value="fullPay">CONTADO</option>
                        {options.map((option) => (
                            <option key={option} value={option}>
                                {option}
                            </option>
                        ))}
                    </select>
                </div>

                <button type="submit">GENERAR</button>
            </form>
        </div>
    );
}

export default InstallmentGenerateForm;