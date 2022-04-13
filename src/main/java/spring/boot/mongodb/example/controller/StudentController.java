package spring.boot.mongodb.example.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

        if(student.getEmail() == null || student.getEmail().isEmpty()) {
            log.error("Student email is empty");
            return ResponseEntity.badRequest().headers(responseHeaders).body("{\"message\": \"Email is required\"}");
        }

        try {
            studentRepository.save(student);
        } catch (Exception e) {
            log.error("Error while saving student", e);
        }
        return ResponseEntity.ok().headers(responseHeaders).body("{\"message\": \"Student created successfully\"}");
    }
}
