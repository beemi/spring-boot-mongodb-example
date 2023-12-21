package spring.boot.mongodb.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import spring.boot.mongodb.example.model.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    Student findByStudentId(final String studentId);

    Student findByEmail(final String email);

    void deleteByStudentId(final String studentId);
}
