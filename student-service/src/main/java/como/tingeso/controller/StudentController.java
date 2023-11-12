package como.tingeso.controller;

import como.tingeso.entity.StudentEntity;
import como.tingeso.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentEntity> newEstudiante(@RequestBody StudentEntity student) {
        StudentEntity studentEntity = studentService.save(student);
        return ResponseEntity.ok(studentEntity);
    }

    @GetMapping
    public ResponseEntity<List<StudentEntity>> listar() {
        List<StudentEntity> studentList = studentService.findAll();
        return ResponseEntity.ok(studentList);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<StudentEntity> getStudentByRut(@PathVariable String rut) {
        StudentEntity student = studentService.findByRut(rut);
        return ResponseEntity.ok(student);
    }
}