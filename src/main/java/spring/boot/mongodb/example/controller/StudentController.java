package spring.boot.mongodb.example.controller;

import io.swagger.v3.oas.annotations.Operation;
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
public final class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Operation(summary = "Create a student", description = "Create a student record", tags = {"student"}, responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Created",
                    content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Student.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/student")
    public ResponseEntity<Object> createStudent(@RequestBody Student student) {

        if (student.getName() == null || student.getName().isEmpty()) {
            log.error("Student name is empty");
            return ResponseEntity.badRequest().headers(new HttpHeaders()).body("{\"message\": \"Name is required\"}");
        }

        if (student.getEmail() == null || student.getEmail().isEmpty()) {
            log.error("Student email is empty");
            return ResponseEntity.badRequest().headers(new HttpHeaders()).body("{\"message\": \"Email is required\"}");
        }

        val existingStudent = studentRepository.findById(student.getId());
        if (existingStudent.isPresent()) {
            log.error("Student already exists");
            return ResponseEntity.badRequest().headers(new HttpHeaders()).body("{\"message\": \"Student already exists\"}");
        }

        try {
            studentRepository.save(student);
        } catch (Exception e) {
            log.error("Error while saving student", e);
        }
        return new ResponseEntity<>(student, new HttpHeaders(), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a student", description = "Update a student record", tags = {"student"}, responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PutMapping("/student")
    public ResponseEntity<Object> updateStudent(@RequestBody Student student) {

        if (student.getName() == null || student.getName().isEmpty()) {
            log.error("Student name is empty");
            return ResponseEntity.badRequest().headers(new HttpHeaders()).body("{\"message\": \"Name is required\"}");
        }

        if (student.getEmail() == null || student.getEmail().isEmpty()) {
            log.error("Student email is empty");
            return ResponseEntity.badRequest().headers(new HttpHeaders()).body("{\"message\": \"Email is required\"}");
        }

        val existingStudent = studentRepository.findById(student.getId());
        if (existingStudent.isEmpty()) {
            log.error("Student does not exist");
            return ResponseEntity.badRequest().headers(new HttpHeaders()).body("{\"message\": \"Student does not exist\"}");
        }

        try {
            studentRepository.save(student);
        } catch (Exception e) {
            log.error("Error while saving student", e);
        }
        return new ResponseEntity<>(student, new HttpHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "Get all students", description = "Get all student records", tags = {"student"}, responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/students")
    public ResponseEntity<Object> getStudents() {

        val students = studentRepository.findAll();
        if (students.isEmpty()) {
            log.error("No students found");
            return ResponseEntity.badRequest().headers(new HttpHeaders()).body("{\"message\": \"No students found\"}");
        }
        return ResponseEntity.ok().headers(new HttpHeaders()).body(students);
    }
    @Operation(summary = "Get student by id", description = "Get student record by Id", tags = {"student"}, responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/student/{id}")
    public ResponseEntity<Object> getStudent(@PathVariable("id") int id) {

        try {
            val student = studentRepository.findById(id);
            return student.<ResponseEntity<Object>>map(value -> new ResponseEntity<>(value, new HttpHeaders(), HttpStatus.OK)).orElseGet(() -> ResponseEntity.badRequest().headers(new HttpHeaders()).body("{\"message\": \"Student not found\"}"));
        } catch (Exception e) {
            log.error("Error while getting student", e);
            return ResponseEntity.badRequest().headers(new HttpHeaders()).body("{\"message\": \"Error while getting student\"}");
        }
    }
    @Operation(summary = "Delete a student", description = "Delete a student record", tags = {"student"}, responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @DeleteMapping("/student/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable("id") int id) {

        try {
            val student = studentRepository.findById(id);
            if (student.isEmpty()) {
                log.error("Student does not exist");
                return ResponseEntity.badRequest().headers(new HttpHeaders()).body("{\"message\": \"Student does not exist\"}");
            }
            studentRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error while deleting student", e);
            return ResponseEntity.badRequest().headers(new HttpHeaders()).body("{\"message\": \"Error while deleting student\"}");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
