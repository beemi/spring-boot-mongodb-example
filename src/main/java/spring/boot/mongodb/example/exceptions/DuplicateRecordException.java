package spring.boot.mongodb.example.exceptions;

import lombok.Getter;
import spring.boot.mongodb.example.model.Student;

@Getter
public class DuplicateRecordException extends RuntimeException {

    public DuplicateRecordException(Student student) {
        super(String.format("Student with id %s already exists.", student.getStudentId()));
    }
}
