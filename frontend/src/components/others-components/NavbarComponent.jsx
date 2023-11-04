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
                <div className='left'>
                    <a onClick={handleClickMenu}>Menú</a>
                    <a onClick={handleClickStudentList}>Listar estudiantes</a>
                </div>
                <div className='right'>
                    <a onClick={handleClickMenu}>
                        <h2>TopEducation</h2>
                    </a>
                </div>

            </nav>
        </div>
    );
}
export default NavbarComponent;