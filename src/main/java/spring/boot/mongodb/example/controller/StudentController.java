package spring.boot.mongodb.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.boot.mongodb.example.exceptions.StudentAlreadyExistsException;
import spring.boot.mongodb.example.model.Student;
import spring.boot.mongodb.example.repository.StudentRepository;
import spring.boot.mongodb.example.service.StudentService;

@Slf4j
@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentRepository studentRepository;

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentRepository studentRepository, StudentService studentService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    @Operation(summary = "Create a student", description = "Create a student record", tags = {"Student"}, responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Created",
                    content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Student.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/v1/student")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student studentRequest) {

        log.info("Creating student {}", studentRequest);

        var student = studentService.getStudentById(studentRequest.getStudentId());
        if (student != null) {
            log.error("Student already exists");
            throw new StudentAlreadyExistsException("Student already exists with ID: " + studentRequest.getStudentId());
        }
        student = studentService.createStudent(studentRequest);
        return new ResponseEntity<>(student, new HttpHeaders(), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a student", description = "Update a student record", tags = {"Student"}, responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PutMapping("/v1/student")
    public ResponseEntity<Object> updateStudent(@RequestBody Student student) {

        if (student.getName() == null || student.getName().isEmpty()) {
            log.error("Student name is empty");
            return ResponseEntity.badRequest().headers(new HttpHeaders()).body("{\"message\": \"Name is required\"}");
        }

        if (student.getEmail() == null || student.getEmail().isEmpty()) {
            log.error("Student email is empty");
            return ResponseEntity.badRequest().headers(new HttpHeaders()).body("{\"message\": \"Email is required\"}");
        }

        try {
            studentRepository.save(student);
        } catch (Exception e) {
            log.error("Error while saving student", e);
        }
        return new ResponseEntity<>(student, new HttpHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "Get all students", description = "Get all student records", tags = {"Student"}, responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/v1/students")
    public ResponseEntity<?> getStudents() {

        final var students = studentService.findAllStudents();
        return ResponseEntity.ok().headers(new HttpHeaders()).body(students);
    }

    @Operation(summary = "Get student by id", description = "Get student record by Id", tags = {"Student"}, responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/v1/student/{id}")
    public ResponseEntity<?> getStudent(@PathVariable("id") String id) {

        final var student = studentService.getStudentById(id);
        if (student == null) {
            log.error("Student not found");
            return ResponseEntity.badRequest().headers(httpHeaders -> httpHeaders.add("Content-Type", "application/json"))
                    .body("{\"message\": \"Student not found\"}");
        }
        return ResponseEntity.ok().headers(new HttpHeaders()).body(student);
    }

    @Operation(summary = "Delete a student", description = "Delete a student record", tags = {"Student"}, responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "No content"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @DeleteMapping("/v1/student/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable String id) {

        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
