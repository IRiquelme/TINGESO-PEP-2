package com.tingeso.services;

import com.tingeso.entities.InstallmentEntity;
import com.tingeso.repositories.InstallmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class InstallmentService {

    @Autowired
    InstallmentRepository installmentRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    ExamService examService;


    public int getRemainingAmount(String rut) {
        Integer remainingAmount = installmentRepository.remainingAmount(rut);
        if (remainingAmount == null) {
            return 0;
        }
        return remainingAmount;
    }

    public LocalDate getLastPaymentDate(String rut) {
        InstallmentEntity lastPaidInstallment = installmentRepository.findLastPaidInstallment(rut);
        if (lastPaidInstallment == null) {
            return null;
        }
        return lastPaidInstallment.getPayDate();
    }

    public int getNumberOfLateInstallments(String rut) {
        return installmentRepository.findLateinstallments(rut).size();
    }

    public String getPaymentType(String rut) {
        int numOf = getNumberOfInstallments(rut);
        if (numOf == 1) {
            return "CONTADO";
        }
        return "CUOTAS";
    }

    public int getNumberOfInstallments(String rut) {
        return installmentRepository.findByRut(rut).size();
    }

    public int getNumberOfPaidInstallments(String rut) {
        return installmentRepository.findPaidInstallments(rut).size();
    }

    public int getTotalAmount(String rut) {
        Integer totalAmount = installmentRepository.totalAmount(rut);
        if (totalAmount == null) {
            return 0;
        }
        return totalAmount;

    }

    public int getTotalPaidAmount(String rut) {
        Integer totalPaidAmount = installmentRepository.totalPaidAmount(rut);
        if (totalPaidAmount == null) {
            return 0;
        }
        return totalPaidAmount;
    }

    public InstallmentEntity markInstallmentAsPaid(long id) {
        InstallmentEntity installment = installmentRepository.findById(id);
        installment.setStatus("PAGADO");
        installment.setPayDate(LocalDate.now());
        return installmentRepository.save(installment);
    }

    // POSIBLE FUNCION DEL FRONTEND
    public ArrayList<String> generateInstallmentOptions(String rut) {
        StudentEntity student = studentService.findByRut(rut); // rest-template
        if (student.getSchoolType().equals("Municipal")) {
            return new ArrayList<>(Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10"));
        } else if (student.getSchoolType().equals("Subvencionado")) {
            return new ArrayList<>(Arrays.asList("2", "3", "4", "5", "6", "7"));
        } else {
            return new ArrayList<>(Arrays.asList("2", "3", "4"));
        }
    }

    public void generateStudentInstallments(String rut, String numberOfInstallments) {
        StudentEntity student = studentService.findByRut(rut); // rest-template
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

    public ArrayList<InstallmentEntity> getInstallmentsByRut(String rut) {
        return installmentRepository.findByRut(rut);
    }

    public void updateInstallmentPaymentSheet() {
        applyInterest();
        applyDiscount();
    }

    private void applyInterest() {
        ArrayList<StudentEntity> allStudent = studentService.getAllStudents();
        allStudent.forEach(student -> applyInterestForEachInstallment(student.getRut()));
    }

    private void applyDiscount() {
        ArrayList<ExamEntity> lastExams = examService.getLastExamRows();
        lastExams.forEach(exam -> applyDiscountForEachInstallment(exam.getRut()));
    }

    private void applyInterestForEachInstallment(String rut) {
        ArrayList<InstallmentEntity> allStudentInstallment = installmentRepository.findByRut(rut);
        int numberOfNoPayInstallment = installmentRepository.findLateinstallments(rut).size();
        allStudentInstallment.forEach(installment -> {
            if (installment.getStatus().equals("PENDIENTE")) {
                int nextAmount = getNewAmountByInterest(installment.getAmount(), numberOfNoPayInstallment);
                installment.setAmount(nextAmount);
                installmentRepository.save(installment);
            }
        });
    }

    private int getNewAmountByInterest(int amount, int numberOfNoPayInstallment) {
        if (numberOfNoPayInstallment == 0) {
            return amount;
        } else if (numberOfNoPayInstallment == 1) {
            return (int) (amount * 1.03);
        } else if (numberOfNoPayInstallment == 2) {
            return (int) (amount * 1.06);
        } else if (numberOfNoPayInstallment == 3) {
            return (int) (amount * 1.09);
        } else {
            return (int) (amount * 1.15);
        }
    }

    private void applyDiscountForEachInstallment(String rut) {
        ArrayList<InstallmentEntity> allStudentInstallments = installmentRepository.findByRut(rut);
        double averageScore = examService.averageScoreByRut(rut);
        allStudentInstallments.forEach(installment -> {
            if (installment.getStatus().equals("PENDIENTE")) {
                int nextAmount = getNewAmountByScore(installment.getAmount(), averageScore);
                installment.setAmount(nextAmount);
                installmentRepository.save(installment);
            }
        });
    }

    private int getNewAmountByScore(int amount, double averageScore) {
        if (averageScore >= 950 && averageScore <= 1000) {
            return (int) (amount * 0.90);
        } else if (averageScore >= 900 && averageScore < 950) {
            return (int) (amount * 0.95);
        } else if (averageScore >= 850 && averageScore < 900) {
            return (int) (amount * 0.98);
        } else {
            return amount;
        }
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

    private double discountPerInstallment(StudentEntity student) {
        return calculateDiscountBySchoolType(student.getSchoolType()) + calculateDiscountByYearsSinceGraduation(student.getYearsSinceGraduation());
    }

    private LocalDate nextExpirationDate(LocalDate date) {
        return date.withDayOfMonth(10);
    }

    private LocalDate nextStartDate(LocalDate date) {
        return date.withDayOfMonth(5).plusMonths(1);
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

    private double calculateDiscountByYearsSinceGraduation(String years) {
        double totalAmount = 1500000;

        return switch (years) {
            case "< 1" -> totalAmount * 0.15;
            case "1", "2" -> totalAmount * 0.08;
            case "3", "4" -> totalAmount * 0.04;
            default -> 0;
        };
    }

    public List<InstallmentEntity> getAllInstallments() {
        return (List<InstallmentEntity>) installmentRepository.findAll();
    }

    public void updateInstallments(List<InstallmentEntity> installments) {
        installments.forEach(installment -> {
            Optional<InstallmentEntity> optionalInstallment = installmentRepository.findById(installment.getId());
            if (optionalInstallment.isEmpty()) {
                System.out.println("No se encontro la cuota con id: " + installment.getId());
                return;
            }
            InstallmentEntity oldInstallment = optionalInstallment.get();
            oldInstallment.setAmount(installment.getAmount());
            installmentRepository.save(oldInstallment);
        });
    }
}
