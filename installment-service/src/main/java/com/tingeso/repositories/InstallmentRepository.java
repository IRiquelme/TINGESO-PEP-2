package com.tingeso.repositories;

import com.tingeso.entities.InstallmentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface InstallmentRepository extends CrudRepository<InstallmentEntity, Long> {

    InstallmentEntity findById(long id);

    @Query(value = "SELECT * FROM installment WHERE installment.rut = :rut ORDER BY installment.installment_number", nativeQuery = true)
    ArrayList<InstallmentEntity> findByRut(@Param("rut") String rut);

    @Query(value = "SELECT * FROM installment WHERE installment.rut = :rut AND installment.installment_number = :installmentNumber", nativeQuery = true)
    InstallmentEntity findByRutAndNumber(@Param("rut") String rut, @Param("installmentNumber") Integer installmentNumber);

    @Query(value = "SELECT * FROM installment WHERE installment.rut = :rut AND installment.status = 'PENDIENTE'  AND installment.expiration_date < CURRENT_DATE", nativeQuery = true)
    ArrayList<InstallmentEntity> findLateinstallments(@Param("rut") String rut);

    @Query(value = "SELECT * FROM installment WHERE rut = :rut AND installment.status = 'PENDIENTE'", nativeQuery = true)
    ArrayList<InstallmentEntity> findUnpaidInstallments(String rut);

    @Query(value = "SELECT * FROM installment WHERE rut = :rut AND installment.status = 'PAGADO'", nativeQuery = true)
    ArrayList<InstallmentEntity> findPaidInstallments(String rut);

    @Query(value = "SELECT * FROM installment WHERE rut = :rut AND installment.status = 'PAGADO' ORDER BY pay_date DESC LIMIT 1", nativeQuery = true)
    InstallmentEntity findLastPaidInstallment(String rut);

    @Query(value = "SELECT SUM(amount) FROM installment WHERE rut = :rut", nativeQuery = true)
    Integer totalAmount(String rut);

    @Query(value = "SELECT SUM(amount) FROM installment WHERE rut = :rut AND installment.status = 'PAGADO'", nativeQuery = true)
    Integer totalPaidAmount(String rut);

    @Query(value = "SELECT SUM(amount) FROM installment WHERE rut = :rut AND installment.status = 'PENDIENTE'", nativeQuery = true)
    Integer remainingAmount(String rut);
}
