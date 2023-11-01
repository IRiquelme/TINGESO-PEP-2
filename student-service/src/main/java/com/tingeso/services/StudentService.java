package com.tingeso.services;

import com.tingeso.entities.StudentEntity;
import com.tingeso.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public List<StudentEntity> getAll() {
        return (List<StudentEntity>) studentRepository.findAll();
    }

    public StudentEntity getByRut(String rut){
        return studentRepository.findByRut(rut);
    }

    public StudentEntity saveStudent(StudentEntity student) {
        if (!isAValidRut(student.getRut())) {
            return null;
        }

        if (isRegister(student.getRut())) {
            return null;
        }

        return studentRepository.save(student);

    }

    public String getStudentName(String rut){
        StudentEntity student = getByRut(rut);
        return student.getNames() + " " + student.getLastNames();
    }

    public boolean isRegister(String rut) {
        return studentRepository.findByRut(rut) != null;
    }

    public boolean isAValidRut(String rut) {
        boolean validation = false;
        try {
            rut = rut.toUpperCase();
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validation = true;
            }
        } catch (Exception ignored) {
        }
        return validation;
    }
}