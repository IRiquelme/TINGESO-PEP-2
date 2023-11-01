package com.tingeso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "installment", uniqueConstraints = @UniqueConstraint(columnNames = {"rut", "installmentNumber"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String status;
    private LocalDate startDate;
    private LocalDate expirationDate;
    private LocalDate payDate;
    private String rut;
    private Integer installmentNumber;
    private Integer amount;
}