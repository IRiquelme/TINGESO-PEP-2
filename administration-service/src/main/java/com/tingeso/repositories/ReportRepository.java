package com.tingeso.repositories;

import com.tingeso.entities.ReportEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends CrudRepository<ReportEntity, Long> {
}
