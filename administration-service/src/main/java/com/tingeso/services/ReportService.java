package com.tingeso.services;

import com.tingeso.entities.ReportEntity;
import com.tingeso.model.InstallmentModel;
import com.tingeso.model.StudentModel;
import com.tingeso.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    ExamService examService;

    @Autowired
    RestTemplate restTemplate;

    private List<InstallmentModel> restGetInstallmentsByRut(String rut) {
        return restTemplate.getForObject("http://installment-service/" + rut, List.class);
    }
    private List<InstallmentModel> restGetPaidInstallment(String rut) {
        return restTemplate.getForObject("http://installment-service/" + rut + "/paid", List.class);
    }

    private List<InstallmentModel> restGetUnpaidInstallment(String rut) {
        return restTemplate.getForObject("http://installment-service/" + rut + "/unpaid", List.class);
    }

    private List<InstallmentModel> restGetLateInstallment(String rut) {
        return restTemplate.getForObject("http://installment-service/" + rut + "/late", List.class);
    }

    private InstallmentModel restGetLastPaidInstallment (String rut) {
        return restTemplate.getForObject("http://installment-service/" + rut + "/last", InstallmentModel.class);
    }

    private String restGetPaymentType (String rut) {
        return restTemplate.getForObject("http://installment-service/" + rut + "/payment", String.class);
    }

    public List<ReportEntity> createReport(List<StudentModel> studentList){
        reportRepository.deleteAll();
        studentList.forEach(this::createStudentReport);
        return (List<ReportEntity>) reportRepository.findAll();
    }

    private void createStudentReport(StudentModel student){
        ReportEntity studentReport = new ReportEntity();
        studentReport.setRut(student.getRut());
        studentReport.setStudentName(student.getNames()+" "+student.getLastNames());
        studentReport.setExamsCount(examService.getExamCountForStudent(student.getRut()));
        studentReport.setAverageScore(examService.averageScoreByRut(student.getRut()));
        studentReport.setTotalAmount(restGetInstallmentsByRut(student.getRut()).stream().mapToInt(InstallmentModel::getAmount).sum());
        studentReport.setPaymentType(restGetPaymentType(student.getRut()));
        studentReport.setNumberOfInstallments(restGetInstallmentsByRut(student.getRut()).size());
        studentReport.setNumberOfPaidInstallments(restGetPaidInstallment(student.getRut()).size());
        studentReport.setTotalPaidAmount(restGetPaidInstallment(student.getRut()).stream().mapToInt(InstallmentModel::getAmount).sum());
        studentReport.setLastPaymentDate(restGetLastPaidInstallment(student.getRut()).getPayDate());
        studentReport.setRemainingAmount(restGetUnpaidInstallment(student.getRut()).stream().mapToInt(InstallmentModel::getAmount).sum());
        studentReport.setNumberOfLateInstallments(restGetLateInstallment(student.getRut()).size());
        reportRepository.save(studentReport);
    }
}