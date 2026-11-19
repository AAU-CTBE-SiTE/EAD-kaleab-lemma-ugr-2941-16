package org.example.studentmanager.service;

import org.example.studentmanager.model.Student;
import org.example.studentmanager.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student createStudent(Student student) {
        return repository.save(student);
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudentById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Student updateStudent(Student student, Long id) {
        Student existingStudent = getStudentById(id);

        if (existingStudent == null) {
            return null;
        }

        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setDepartment(student.getDepartment());

        return repository.save(existingStudent);
    }

    public void deleteStudent(Long id) {
        Student student = getStudentById(id);

        if (student != null) {
            repository.delete(student);
        }
    }
}
