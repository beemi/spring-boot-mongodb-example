package spring.boot.mongodb.example.service;

import org.springframework.stereotype.Component;
import spring.boot.mongodb.example.model.Student;

import java.util.List;

@Component
public interface StudentService {

    Student createStudent(Student student);

    Student getStudentById(String studentId);

    Student getStudentByEmail(String email);

    Student updateStudent(Student student);

    void deleteStudent(String studentId);

    List<Student> findAllStudents();
}
