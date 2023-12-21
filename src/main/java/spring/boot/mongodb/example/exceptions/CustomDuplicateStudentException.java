package spring.boot.mongodb.example.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomDuplicateStudentException extends RuntimeException {
    public CustomDuplicateStudentException(String message) {
        super(message);
    }
}
