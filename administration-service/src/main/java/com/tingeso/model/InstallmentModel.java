package com.tingeso.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InstallmentModel {
    private Long id;
    private String status;
    private LocalDate startDate;
    private LocalDate expirationDate;
    private LocalDate payDate;
    private String rut;
    private Integer installmentNumber;
    private Integer amount;
}