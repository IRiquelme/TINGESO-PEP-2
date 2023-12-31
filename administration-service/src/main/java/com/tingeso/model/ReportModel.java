package com.tingeso.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReportModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rut;
    private String studentName;
    private Integer examsCount;
    private double averageScore;
    private Integer totalAmount;
    private String paymentType;
    private Integer numberOfInstallments;
    private Integer numberOfPaidInstallments;
    private Integer numberOfLateInstallments;
    private Integer totalPaidAmount;
    private LocalDate lastPaymentDate;
    private Integer remainingAmount;
}