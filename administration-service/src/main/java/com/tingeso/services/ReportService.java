package com.tingeso.services;

import com.tingeso.entities.ReportEntity;
import com.tingeso.model.StudentModel;
import com.tingeso.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    ExamService examService;

    public List<ReportEntity> createReport(){
        reportRepository.deleteAll();
        List<StudentModel> allStudents = studentService.getAllStudents(); // RestTemplate
        allStudents.forEach(student -> createSummaryRow(student.getRut()));
        return (List<ReportEntity>) reportRepository.findAll();
    }

    private void createSummaryRow(String rut){
        ReportEntity newRow = new ReportEntity();
        newRow.setRut(rut);
        newRow.setStudentName(studentService.getStudentName(rut));
        newRow.setExamsCount(examService.getExamCountForStudent(rut));
        newRow.setAverageScore(examService.averageScoreByRut(rut));
        newRow.setTotalAmount(installmentService.getTotalAmount(rut));
        newRow.setPaymentType(installmentService.getPaymentType(rut));
        newRow.setNumberOfInstallments(installmentService.getNumberOfInstallments(rut));
        newRow.setNumberOfPaidInstallments(installmentService.getNumberOfPaidInstallments(rut));
        newRow.setTotalPaidAmount(installmentService.getTotalPaidAmount(rut));
        newRow.setLastPaymentDate(installmentService.getLastPaymentDate(rut));
        newRow.setRemainingAmount(installmentService.getRemainingAmount(rut));
        newRow.setNumberOfLateInstallments(installmentService.getNumberOfLateInstallments(rut));
        reportRepository.save(newRow);
    }
}