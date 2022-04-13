package spring.boot.mongodb.example.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.boot.mongodb.example.model.Student;
import spring.boot.mongodb.example.repository.StudentRepository;

@Slf4j
@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/student")
    public ResponseEntity<Object> createStudent(@RequestBody Student student) {

        val responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        if (student.getName() == null || student.getName().isEmpty()) {
            log.error("Student name is empty");
            return ResponseEntity.badRequest().headers(responseHeaders).body("{\"message\": \"Name is required\"}");
        }

        if (student.getEmail() == null || student.getEmail().isEmpty()) {
            log.error("Student email is empty");
            return ResponseEntity.badRequest().headers(responseHeaders).body("{\"message\": \"Email is required\"}");
        }

        val existingStudent = studentRepository.findById(student.getId());
        if (existingStudent.isPresent()) {
            log.error("Student already exists");
            return ResponseEntity.badRequest().headers(responseHeaders).body("{\"message\": \"Student already exists\"}");
        }

        try {
            studentRepository.save(student);
        } catch (Exception e) {
            log.error("Error while saving student", e);
        }
        return new ResponseEntity<>(student, new HttpHeaders(), HttpStatus.CREATED);
    }


    @GetMapping("/students")
    public ResponseEntity<Object> getStudents() {
        val responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        val students = studentRepository.findAll();
        if (students.isEmpty()) {
            log.error("No students found");
            return ResponseEntity.badRequest().headers(responseHeaders).body("{\"message\": \"No students found\"}");
        }
        return ResponseEntity.ok().headers(responseHeaders).body(students);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<Object> getStudent(@PathVariable("id") int id) {

        val responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        try {
            val student = studentRepository.findById(id);
            return student.<ResponseEntity<Object>>map(value -> new ResponseEntity<>(value, new HttpHeaders(), HttpStatus.OK)).orElseGet(() -> ResponseEntity.badRequest().headers(responseHeaders).body("{\"message\": \"Student not found\"}"));
        } catch (Exception e) {
            log.error("Error while getting student", e);
            return ResponseEntity.badRequest().headers(responseHeaders).body("{\"message\": \"Error while getting student\"}");
        }
    }

    @DeleteMapping("/student")
    public ResponseEntity<Object> deleteStudent(@RequestParam("id") final int id) {
        val responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        val student = studentRepository.findById(id);
        if (student.isEmpty()) {
            log.error("No student found");
            return ResponseEntity.badRequest().headers(responseHeaders).body("{\"message\": \"No student found\"}");
        }
        studentRepository.delete(student.get());
        return ResponseEntity.ok().headers(responseHeaders).body("{\"message\": \"Student deleted successfully\"}");
    }
}
