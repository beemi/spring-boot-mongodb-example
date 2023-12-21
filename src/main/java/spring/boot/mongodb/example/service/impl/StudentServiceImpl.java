package spring.boot.mongodb.example.service.impl;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.boot.mongodb.example.model.Student;
import spring.boot.mongodb.example.repository.StudentRepository;
import spring.boot.mongodb.example.service.StudentService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    private final  StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student studentRequest) {
        if (StringUtils.isEmpty(studentRequest.getCreatedAt())) {
            studentRequest.setCreatedAt(String.valueOf(System.currentTimeMillis()));
        }
        if (StringUtils.isEmpty(studentRequest.getUpdatedAt())) {
            studentRequest.setUpdatedAt(String.valueOf(System.currentTimeMillis()));
        }

        studentRequest.setStudentId(UUID.randomUUID().toString());

        return studentRepository.save(studentRequest);
    }

    @Override
    public Student getStudentById(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    @Override
    public Student getStudentByEmail(final String email) {
        return null;
    }

    @Override
    public Student updateStudent(final Student student) {
        return null;
    }

    @Override
    public void deleteStudent(final String studentId) {
        studentRepository.deleteByStudentId(studentId);
    }

    @Override
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }
}
