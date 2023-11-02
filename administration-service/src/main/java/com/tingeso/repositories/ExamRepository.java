package com.tingeso.repositories;

import com.tingeso.entities.ExamEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ExamRepository extends CrudRepository<ExamEntity, Long> {

    @Query(value = "SELECT * FROM exam WHERE date = (SELECT MAX(date) FROM exam)", nativeQuery = true)
    ArrayList<ExamEntity> findLastExam();

    @Query(value = "SELECT AVG(score) FROM exam WHERE rut = :rut", nativeQuery = true)
    Double averageScore(String rut);

    ArrayList<ExamEntity> findByRut(String rut);
}