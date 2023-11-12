package com.tingeso.service;

import com.tingeso.model.InstallmentModel;
import com.tingeso.model.ReportModel;
import com.tingeso.model.StudentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    ExamService examService;

    @Autowired
    RestTemplate restTemplate;

    private List<InstallmentModel> restGetInstallmentsByRut(String rut) {
        ResponseEntity<List<InstallmentModel>> response = restTemplate.exchange(
                "http://localhost:8080/installment/" + rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    private List<InstallmentModel> restGetPaidInstallment(String rut) {
        ResponseEntity<List<InstallmentModel>> response = restTemplate.exchange(
                "http://localhost:8080/installment/" + rut + "/paid",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    private List<InstallmentModel> restGetUnpaidInstallment(String rut) {
        ResponseEntity<List<InstallmentModel>> response = restTemplate.exchange(
                "http://localhost:8080/installment/" + rut + "/unpaid",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    private List<InstallmentModel> restGetLateInstallment(String rut) {
        ResponseEntity<List<InstallmentModel>> response = restTemplate.exchange(
                "http://localhost:8080/installment/" + rut + "/late",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    private LocalDate restGetLastPaidInstallment (String rut) {
        ResponseEntity<InstallmentModel> response = restTemplate.exchange(
                "http://localhost:8080/installment/" + rut + "/last",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        InstallmentModel installment = response.getBody();
        if (installment != null) {
            return installment.getPayDate();
        }
        return null;
    }

    private String restGetPaymentType (String rut) {
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/installment/" + rut + "/payment",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    public List<ReportModel> createReport(List<StudentModel> studentList){
        List<ReportModel> reporList = new ArrayList<>();
        studentList.forEach(
                studentModel -> {
                    reporList.add(createStudentReport(studentModel));
                }
        );
        return reporList;
    }

    private ReportModel createStudentReport(StudentModel student){
        ReportModel studentReport = new ReportModel();
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
        return studentReport;
    }
}