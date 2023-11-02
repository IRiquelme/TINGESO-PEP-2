package com.tingeso.services;

import com.tingeso.entities.ExamEntity;
import com.tingeso.repositories.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class ExamService {
    @Autowired
    ExamRepository examRepository;

    public String upload(MultipartFile exam){
        if (!exam.isEmpty()) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(exam.getInputStream()))) {
                String line;
                boolean firstLine = true;

                while ((line = br.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }

                    String[] attributes = line.split(",");
                    ExamEntity newExamRow = new ExamEntity();
                    newExamRow.setRut(attributes[0]);
                    newExamRow.setDate(LocalDate.parse(attributes[1]));
                    newExamRow.setScore(Double.parseDouble(attributes[2]));
                    examRepository.save(newExamRow);
                }
                return "Archivo cargado con exito";
            } catch (IOException e) {
                return "Error al leer el archivo";
            }
        }
        return "Error al cargar el archivo";
    }

    public ArrayList<ExamEntity> getLastExamRows(){
        return examRepository.findLastExam();
    }

    public double averageScoreByRut(String rut){
        Double average = examRepository.averageScore(rut);
        if (average == null){
            return 0;
        }
        return average;
    }

    public int getExamCountForStudent(String rut){
        return examRepository.findByRut(rut).size();
    }
}
