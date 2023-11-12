package com.tingeso.repository;

import com.tingeso.entity.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, String> {

    @Query(value = "SELECT * FROM exam WHERE date = (SELECT MAX(date) FROM exam)", nativeQuery = true)
    List<ExamEntity> findLastExamRows();

    @Query(value = "SELECT AVG(score) FROM exam WHERE rut = :rut", nativeQuery = true)
    Double averageScore(String rut);

    List<ExamEntity> findByRut(String rut);
}