package com.tingeso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "report")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
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