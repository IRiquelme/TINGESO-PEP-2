package com.tingeso.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentModel {
    private String rut;
    private String names;
    private String lastNames;
    private String dateBirth;
    private String schoolType;
    private String schoolName;
    private String yearsSinceGraduation;
}
