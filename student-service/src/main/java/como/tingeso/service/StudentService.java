package como.tingeso.service;

import como.tingeso.entity.StudentEntity;
import como.tingeso.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public List<StudentEntity> findAll() {
        return studentRepository.findAll();
    }

    public StudentEntity findByRut(String rut) {
        return studentRepository.findByRut(rut);
    }

    public StudentEntity save(StudentEntity student) {
        if (isRegister(student.getRut())) {
            return null;
        }
        return studentRepository.save(student);
    }

    private boolean isRegister(String rut) {
        return studentRepository.findByRut(rut) != null;
    }
}