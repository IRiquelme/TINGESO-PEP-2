package com.tingeso.service;

import com.tingeso.entity.InstallmentEntity;
import com.tingeso.model.StudentModel;
import com.tingeso.repository.InstallmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;


@Service
public class InstallmentService {
    @Autowired
    InstallmentRepository installmentRepository;

    @Autowired
    RestTemplate restTemplate;

    public void markInstallmentAsPaid(int id) {
        InstallmentEntity installment = installmentRepository.findById(id);
        installment.setStatus("PAGADO");
        installment.setPayDate(LocalDate.now());
        installmentRepository.save(installment);
    }

    public void generateStudentInstallments(String rut, String numberOfInstallments) {
        StudentModel student = restGetStudent(rut);
        LocalDate date = LocalDate.now();
        int totalAmount = 1500000;
        if (numberOfInstallments.equals("fullPay")) {
            totalAmount = (int) (totalAmount * 0.5);
            createInstallment(rut, 1, "PAGADO", date, date, totalAmount);
        } else {
            double discount = discountPerInstallment(student);
            totalAmount = (int) (totalAmount - discount);
            int amountPerInstallment = totalAmount / Integer.parseInt(numberOfInstallments);
            for (int i = 1; i <= Integer.parseInt(numberOfInstallments); i++) {
                date = nextStartDate(date);
                createInstallment(rut, i, "PENDIENTE", date, nextExpirationDate(date), amountPerInstallment);
            }
        }
    }

    public List<InstallmentEntity> findAll() {
        return installmentRepository.findAll();
    }

    public void updateInstallments(List<InstallmentEntity> installments) {
        installments.forEach(installment -> {
            System.out.println(installment);
            InstallmentEntity oldInstallment = installmentRepository.findByRutAndNumber(installment.getRut(), installment.getInstallmentNumber());
            oldInstallment.setAmount(installment.getAmount());
            installmentRepository.save(oldInstallment);
        });
    }

    public List<InstallmentEntity> findUnpaidInstallmentsByRut(String rut) {
        return installmentRepository.findUnpaidInstallments(rut);
    }

    public List<InstallmentEntity> findPaidInstallmentsByRut(String rut) {
        return installmentRepository.findPaidInstallments(rut);
    }

    public List<InstallmentEntity> findLateInstallmentsByRut(String rut) {
        return installmentRepository.findLateinstallments(rut);
    }

    public String getPaymentType(String rut) {
        int numberOfInstallments = installmentRepository.findByRut(rut).size();
        if (numberOfInstallments == 1) {
            return "CONTADO";
        } else {
            return "CUOTAS";
        }
    }

    public InstallmentEntity findLastPaidInstallment(String rut) {
        return installmentRepository.findLastPaidInstallment(rut);
    }

    public List<InstallmentEntity> findByRut(String rut) {
        return installmentRepository.findByRut(rut);
    }

    private void createInstallment(String rut, int installmentNumber, String status, LocalDate startDate, LocalDate expirationDate, Integer amount) {
        InstallmentEntity installmentEntity = new InstallmentEntity();
        installmentEntity.setRut(rut);
        installmentEntity.setInstallmentNumber(installmentNumber);
        installmentEntity.setStatus(status);
        installmentEntity.setStartDate(startDate);
        installmentEntity.setExpirationDate(expirationDate);
        installmentEntity.setPayDate(null);
        installmentEntity.setAmount(amount);
        installmentRepository.save(installmentEntity);
    }

    private double discountPerInstallment(StudentModel student) {
        return calculateDiscountBySchoolType(student.getSchoolType()) + calculateDiscountByYearsSinceGraduation(student.getYearsSinceGraduation());
    }

    private double calculateDiscountByYearsSinceGraduation(String years) {
        double totalAmount = 1500000;

        return switch (years) {
            case "0" -> totalAmount * 0.15;
            case "1", "2" -> totalAmount * 0.08;
            case "3", "4" -> totalAmount * 0.04;
            default -> 0;
        };
    }

    private double calculateDiscountBySchoolType(String schoolType) {
        double totalAmount = 1500000;

        if (schoolType.equals("Municipal")) {
            return totalAmount * 0.2;
        } else if (schoolType.equals("Subvencionado")) {
            return totalAmount * 0.1;
        } else {
            return 0;
        }
    }

    private LocalDate nextExpirationDate(LocalDate date) {
        return date.withDayOfMonth(10);
    }

    private LocalDate nextStartDate(LocalDate date) {
        return date.withDayOfMonth(5).plusMonths(1);
    }

    private StudentModel restGetStudent(String rut) {
        ResponseEntity<StudentModel> response = restTemplate.exchange(
                "http://localhost:8080/student/" + rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }
}