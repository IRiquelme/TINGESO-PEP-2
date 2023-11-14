package com.tingeso.controller;

import com.tingeso.model.ReportModel;
import com.tingeso.service.AdministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tingeso.entity.ExamEntity;
import com.tingeso.model.StudentModel;
import com.tingeso.service.ExamService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/administration")
public class AdministratorController {
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
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok().body(examList);
    }

    @PostMapping("/report")
    public ResponseEntity<List<ReportModel>> report(){
        List<ReportModel> report = administrationService.createReport();
        if (report.isEmpty()){
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok().body(report);
    }
}