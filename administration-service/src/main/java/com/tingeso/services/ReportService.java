package com.tingeso.services;

import com.tingeso.entities.ReportEntity;
import com.tingeso.models.InstallmentModel;
import com.tingeso.models.StudentModel;
import com.tingeso.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
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
        return restTemplate.exchange(
                "http://installment-service/installment/" + rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InstallmentModel>>() {}
        ).getBody();
    }

    private List<InstallmentModel> restGetPaidInstallment(String rut) {
        return restTemplate.exchange(
                "http://installment-service/installment/" + rut + "/paid",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InstallmentModel>>() {}
        ).getBody();
    }

    private List<InstallmentModel> restGetUnpaidInstallment(String rut) {
        return restTemplate.exchange(
                "http://installment-service/installment/" + rut + "/unpaid",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InstallmentModel>>() {}
        ).getBody();
    }

    private List<InstallmentModel> restGetLateInstallment(String rut) {
        return restTemplate.exchange(
                "http://installment-service/installment/" + rut + "/late",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InstallmentModel>>() {}
        ).getBody();
    }

    private LocalDate restGetLastPaidInstallment (String rut) {
        InstallmentModel installment = restTemplate.getForObject("http://installment-service/installment/" + rut + "/last", InstallmentModel.class);
        if (installment != null) {
            return installment.getPayDate();
        }
        return null;
    }

    private String restGetPaymentType (String rut) {
        return restTemplate.getForObject("http://installment-service/installment/" + rut + "/payment", String.class);
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
        studentReport.setLastPaymentDate(restGetLastPaidInstallment(student.getRut()));
        studentReport.setRemainingAmount(restGetUnpaidInstallment(student.getRut()).stream().mapToInt(InstallmentModel::getAmount).sum());
        studentReport.setNumberOfLateInstallments(restGetLateInstallment(student.getRut()).size());
        reportRepository.save(studentReport);
    }
}