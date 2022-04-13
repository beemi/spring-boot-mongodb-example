package spring.boot.mongodb.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import spring.boot.mongodb.example.model.Student;

public interface StudentRepository extends MongoRepository<Student, Integer> {
}
