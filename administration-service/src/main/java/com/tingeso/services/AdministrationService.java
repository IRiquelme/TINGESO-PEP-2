package com.tingeso.services;

import com.tingeso.entities.ExamEntity;
import com.tingeso.entities.ReportEntity;
import com.tingeso.models.InstallmentModel;
import com.tingeso.models.StudentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class AdministrationService {
    @Autowired
    ExamService examService;

    @Autowired
    ReportService reportService;

    @Autowired
    RestTemplate restTemplate;

    private List<StudentModel> restGetStudents() {
        return restTemplate.exchange(
                "http://student-service/student",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StudentModel>>() {}
        ).getBody();
    }

    private List<InstallmentModel> restGetInstallmentsByRut(String rut) {
        return restTemplate.exchange(
                "http://installment-service/installment/" + rut,
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

    public String uploadExam(MultipartFile exam) {
        return examService.upload(exam);
    }

    public List<ExamEntity> getLastExamRows() {
        return examService.getLastExamRows();
    }

    public List<ReportEntity> createReport() {
        List<StudentModel> studentList = restGetStudents();
        return reportService.createReport(studentList);
    }
    public String updateInstallments() {
        List<StudentModel> students = restGetStudents();
        students.forEach(student -> {
            List<InstallmentModel> studentInstallments = restGetInstallmentsByRut(student.getRut());
            double interest = getStudentInterest(student);
            double discount = getStudentDiscount(student);
            studentInstallments.forEach(installment -> {
                if (installment.getStatus().equals("PENDIENTE")) {
                    Integer newAmount = (int) (installment.getAmount()*(1 + interest)* (1-discount));
                    installment.setAmount(newAmount);
                }
            });
            restTemplate.postForObject("http://installment-service/installment/update", studentInstallments, String.class);
        });
        return "Cuotas actualizadas";
    }

    private double getStudentInterest(StudentModel student) {
        int numberOfUnpaidInstallment = restGetUnpaidInstallment(student.getRut()).size();
        if (numberOfUnpaidInstallment == 0) {
            return 0;
        } else if (numberOfUnpaidInstallment == 1) {
            return .03;
        } else if (numberOfUnpaidInstallment == 2) {
            return .06;
        } else if (numberOfUnpaidInstallment == 3) {
            return .09;
        } else {
            return .15;
        }
    }

    private double getStudentDiscount(StudentModel student) {
        double averageScore = examService.averageScoreByRut(student.getRut());
        if (averageScore >= 950 && averageScore <= 1000) {
            return .10;
        } else if (averageScore >= 900 && averageScore < 950) {
            return .05;
        } else if (averageScore >= 850 && averageScore < 900) {
            return .02;
        } else {
            return 0;
        }
    }
}
