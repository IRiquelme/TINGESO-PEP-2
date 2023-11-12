package com.tingeso.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "installment", uniqueConstraints = @UniqueConstraint(columnNames = {"rut", "installmentNumber"}))
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InstallmentEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String status;
    private LocalDate startDate;
    private LocalDate expirationDate;
    private LocalDate payDate;
    private String rut;
    private Integer installmentNumber;
    private Integer amount;
}