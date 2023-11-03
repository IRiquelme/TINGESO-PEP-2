package com.tingeso.controllers;

import com.tingeso.entities.InstallmentEntity;
import com.tingeso.services.InstallmentService;
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
    public ResponseEntity<?> getAllInstallments() {
        List<InstallmentEntity> installments = installmentService.getAllInstallments();
        return ResponseEntity.ok(installments);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<?> getInstallmentsByRut(@PathVariable String rut){
        List<InstallmentEntity> listOfInstallments = installmentService.getInstallmentsByRut(rut);
        return ResponseEntity.ok(listOfInstallments);
    }

    @GetMapping("/{rut}/unpaid")
    public ResponseEntity<?> getUnpaidInstallmentsByRut(@PathVariable String rut){
        List<InstallmentEntity> listOfInstallments = installmentService.getUnpaidInstallmentsByRut(rut);
        return ResponseEntity.ok(listOfInstallments);
    }

    @GetMapping("/{rut}/paid")
    public ResponseEntity<?> getPaidInstallmentsByRut(@PathVariable String rut){
        List<InstallmentEntity> listOfInstallments = installmentService.getPaidInstallmentsByRut(rut);
        return ResponseEntity.ok(listOfInstallments);
    }

    @GetMapping("/{rut}/late")
    public ResponseEntity<?> getLateInstallmentsByRut(@PathVariable String rut){
        List<InstallmentEntity> listOfInstallments = installmentService.getLateInstallmentsByRut(rut);
        return ResponseEntity.ok(listOfInstallments);
    }

    @GetMapping("/{rut}/last")
    public ResponseEntity<?> getLastPaidInstallment(@PathVariable String rut){
        InstallmentEntity installment = installmentService.getLastPaidInstallment(rut);
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
    public ResponseEntity<InstallmentEntity> markInstallmentAsPaid(@PathVariable long id) {
        InstallmentEntity installment = installmentService.markInstallmentAsPaid(id);
        return ResponseEntity.ok(installment);
    }

    @PostMapping("/update")
    public ResponseEntity<String>  installmentUpdate(@RequestBody List<InstallmentEntity> installments){
        installmentService.updateInstallments(installments);
        return ResponseEntity.ok("Cuotas actualizadas");
    }
}