package com.tingeso.repository;

import com.tingeso.entity.InstallmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstallmentRepository extends JpaRepository<InstallmentEntity, String> {

    InstallmentEntity findById(int id);

    @Query(value = "SELECT * FROM installment WHERE installment.rut = :rut ORDER BY installment.installment_number", nativeQuery = true)
    List<InstallmentEntity> findByRut(@Param("rut") String rut);

    @Query(value = "SELECT * FROM installment WHERE installment.rut = :rut AND installment.installment_number = :installmentNumber", nativeQuery = true)
    InstallmentEntity findByRutAndNumber(@Param("rut") String rut, @Param("installmentNumber") Integer installmentNumber);

    @Query(value = "SELECT * FROM installment WHERE installment.rut = :rut AND installment.status = 'PENDIENTE'  AND installment.expiration_date < CURRENT_DATE", nativeQuery = true)
    List<InstallmentEntity> findLateinstallments(@Param("rut") String rut);

    @Query(value = "SELECT * FROM installment WHERE rut = :rut AND installment.status = 'PENDIENTE'", nativeQuery = true)
    List<InstallmentEntity> findUnpaidInstallments(String rut);

    @Query(value = "SELECT * FROM installment WHERE rut = :rut AND installment.status = 'PAGADO'", nativeQuery = true)
    List<InstallmentEntity> findPaidInstallments(String rut);

    @Query(value = "SELECT * FROM installment WHERE rut = :rut AND installment.status = 'PAGADO' ORDER BY pay_date DESC LIMIT 1", nativeQuery = true)
    InstallmentEntity findLastPaidInstallment(String rut);
    @Query(value = "DELETE FROM installment WHERE rut = :rut", nativeQuery = true)
    void deleteAllByRut(String rut);
}