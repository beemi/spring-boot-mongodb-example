package spring.boot.mongodb.example.model;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Student")
public class Student {

    @Id
    @Parameter(description = "Student id")
    private int id;
    @Parameter(description = "Student name")
    private String name;
    @Parameter(description = "Student email")
    private String email;
    @Parameter(description = "Student phone")
    private String phone;
    @Parameter(description = "Student address")
    private String address;
    @Parameter(description = "Student city")
    private String city;
    @Parameter(description = "Student state")
    private String state;
    @Parameter(description = "Student zip")
    private String zip;
    @Parameter(description = "Student country")
    private String country;
}
