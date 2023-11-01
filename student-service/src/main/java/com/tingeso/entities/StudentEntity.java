package com.tingeso.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {
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