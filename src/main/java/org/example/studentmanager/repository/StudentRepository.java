package org.example.studentmanager.repository;

import org.example.studentmanager.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);

    List<Student> findByDepartment(String department);
}
