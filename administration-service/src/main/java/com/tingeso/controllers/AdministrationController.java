package com.tingeso.controllers;

import com.tingeso.entities.ExamEntity;
import com.tingeso.entities.ReportEntity;
import com.tingeso.services.AdministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/administration")
public class AdministrationController {

    @Autowired
    AdministrationService administrationService;

    @PostMapping("/update")
    public ResponseEntity<?> updateInstallments(){
        String message = administrationService.updateInstallments();
        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/exam")
    public ResponseEntity<?> examUpload(@RequestParam("exam") MultipartFile exam){
        String message = administrationService.uploadExam(exam);
        return ResponseEntity.ok().body(message);
    }
    @GetMapping("/exam")
    public ResponseEntity<List<ExamEntity>> examList(Model model){
        List<ExamEntity> examList = administrationService.getLastExamRows();
        if (examList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(examList);
    }

    @GetMapping("/report")
    public ResponseEntity<?> report(){
        List<ReportEntity> report = administrationService.createReport();
        if (report.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(report);
    }
}
