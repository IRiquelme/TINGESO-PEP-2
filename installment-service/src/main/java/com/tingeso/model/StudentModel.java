package com.tingeso.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentModel {
    @Id
    @Column(unique = true, nullable = false)
    @NotNull
    private String rut;

    private String names;
    private String lastNames;
    private String dateBirth;
    private String schoolType;
    private String schoolName;
    private String yearsSinceGraduation;
}