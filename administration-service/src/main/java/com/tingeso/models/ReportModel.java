package com.tingeso.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
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