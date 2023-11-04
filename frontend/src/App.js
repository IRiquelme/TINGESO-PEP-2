import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MenuComponent from './components/MenuComponent';
import ExamUploadComponent from './components/ExamUploadComponent';
import ExamListComponent from './components/ExamListComponent';
import StudentListComponent from './components/StudentListComponent';
import StudentRegisterComponent from './components/StudentRegisterComponent';
import InstallmentListComponent from './components/InstallmentListComponent';
import InstallmentGenerateComponent from './components/InstallmentGenerateComponent';
import ReporListComponent from './components/ReportListComponent';

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<MenuComponent/>} />
          <Route path="/exam-upload" element={<ExamUploadComponent />} />
          <Route path="/exam-list" element={<ExamListComponent />} />
          <Route path="/student-list" element={<StudentListComponent />} />
          <Route path="/student-register" element={<StudentRegisterComponent />} />
          <Route path="/installment-list" element={<InstallmentListComponent />} />
          <Route path="/installment-generate" element={<InstallmentGenerateComponent />} />
          <Route path="/report-list" element={<ReporListComponent />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
