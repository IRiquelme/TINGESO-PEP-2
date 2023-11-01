package com.tingeso.controllers;

import com.tingeso.entities.StudentEntity;
import com.tingeso.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @GetMapping
    public ResponseEntity<List<StudentEntity>> getAll() {
        List<StudentEntity> students = studentService.getAll();
        if (students.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<StudentEntity> getByRut(@PathVariable String rut) {
        StudentEntity student = studentService.getByRut(rut);
        if (student == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<?> saveStudent(@RequestBody StudentEntity student) {

        StudentEntity response = studentService.saveStudent(student);

        if (response == null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("El RUT ingresado ya esta registrado.");
        }

        return ResponseEntity.ok(response);
    }
}