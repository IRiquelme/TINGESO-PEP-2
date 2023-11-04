/* eslint-disable jsx-a11y/anchor-is-valid */
import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../../styles/navbar.css';

function NavbarComponent() {
    const navigate = useNavigate();

    const handleClickStudentList = () => {
        navigate("/student-list");
    };

    const handleClickMenu = () => {
        navigate("/");
    };

    return (
        <div>
            <nav className="nav">
                <div>
                    <a onClick={handleClickMenu}>Menú</a>
                    <a onClick={handleClickStudentList}>Listar estudiantes</a>
                </div>
                <div>
                    <h1>TopEducation</h1>
                </div>
            </nav>
        </div>
    );
}

export default NavbarComponent;