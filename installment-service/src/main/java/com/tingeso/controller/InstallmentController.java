package com.tingeso.controller;

import com.tingeso.entity.InstallmentEntity;
import com.tingeso.service.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/installment")
public class InstallmentController {
    @Autowired
    InstallmentService installmentService;

    @GetMapping
    public ResponseEntity<List<InstallmentEntity>> findAll() {
        List<InstallmentEntity> installments = installmentService.findAll();
        return ResponseEntity.ok(installments);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<?> finByRut(@PathVariable String rut){
        List<InstallmentEntity> listOfInstallments = installmentService.findByRut(rut);
        return ResponseEntity.ok(listOfInstallments);
    }

    @GetMapping("/{rut}/unpaid")
    public ResponseEntity<List<InstallmentEntity>> findUnpaidInstallmentsByRut(@PathVariable String rut){
        List<InstallmentEntity> listOfInstallments = installmentService.findUnpaidInstallmentsByRut(rut);
        return ResponseEntity.ok(listOfInstallments);
    }

    @GetMapping("/{rut}/paid")
    public ResponseEntity<List<InstallmentEntity>> findPaidInstallmentsByRut(@PathVariable String rut){
        List<InstallmentEntity> listOfInstallments = installmentService.findPaidInstallmentsByRut(rut);
        return ResponseEntity.ok(listOfInstallments);
    }

    @GetMapping("/{rut}/late")
    public ResponseEntity<List<InstallmentEntity>> findLateInstallmentsByRut(@PathVariable String rut){
        List<InstallmentEntity> listOfInstallments = installmentService.findLateInstallmentsByRut(rut);
        return ResponseEntity.ok(listOfInstallments);
    }

    @GetMapping("/{rut}/last")
    public ResponseEntity<InstallmentEntity> findLastPaidInstallment(@PathVariable String rut){
        InstallmentEntity installment = installmentService.findLastPaidInstallment(rut);
        return ResponseEntity.ok(installment);
    }

    @GetMapping("/{rut}/payment")
    public ResponseEntity<String> getPaymentType (@PathVariable String rut){
        String paymentType = installmentService.getPaymentType(rut);
        return ResponseEntity.ok(paymentType);
    }

    @PostMapping("{rut}/{numberOfInstallments}")
    public ResponseEntity<String> installmentGenerate(@PathVariable String rut, @PathVariable String numberOfInstallments) {
        installmentService.generateStudentInstallments(rut, numberOfInstallments);
        return ResponseEntity.ok("Cuotas generadas");
    }

    @PostMapping("/pay/{id}")
    public ResponseEntity<String> markInstallmentAsPaid(@PathVariable int id) {
        installmentService.markInstallmentAsPaid(id);
        return ResponseEntity.ok("Cuota pagada");
    }

    @PostMapping("/update")
    public ResponseEntity<String>  installmentUpdate(@RequestBody List<InstallmentEntity> installments){
        installmentService.updateInstallments(installments);
        return ResponseEntity.ok("Cuotas actualizadas");
    }
}