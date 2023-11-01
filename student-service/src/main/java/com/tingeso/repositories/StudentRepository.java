package com.tingeso.repositories;

import com.tingeso.entities.StudentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, Long> {
    StudentEntity findByRut(String rut);
}