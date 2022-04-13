package spring.boot.mongodb.example;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
@Slf4j
@SpringBootApplication
@ServletComponentScan
@OpenAPIDefinition(info = @Info(title = "Student Application", version = "1.0"))
public class StudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
        log.info("Student Application Started");
    }

}
