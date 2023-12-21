package spring.boot.mongodb.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Student")
public class Student implements Serializable {

    @Id
    @Parameter(hidden = true, description = "MongoDB generated id")
    @JsonIgnore
    private ObjectId mongoId;

    @Indexed(background = true)
    @Parameter(hidden = true)
    private String studentId;

    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String createdAt;
    private String updatedAt;
}
