package com.tingeso.controllers;

import com.tingeso.entities.InstallmentEntity;
import com.tingeso.services.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/installment")
public class InstallmentController {

    @Autowired
    InstallmentService installmentService;

    @GetMapping
    public ResponseEntity<?> getAllInstallments() {
        List<InstallmentEntity> installments = installmentService.getAllInstallments();
        if (installments.isEmpty()){
            return ResponseEntity
                    .status(404)
                    .body(null);
        }
        return ResponseEntity.ok(installments);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<?> getInstallmentsByRut(@PathVariable String rut){
        List<InstallmentEntity> listOfInstallments = installmentService.getInstallmentsByRut(rut);
        if (listOfInstallments.isEmpty()){
            return ResponseEntity
                    .status(404)
                    .body(null);
        }
        return ResponseEntity.ok(listOfInstallments);
    }

    @PostMapping
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