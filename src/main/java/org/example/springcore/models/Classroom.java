package org.example.springcore.models;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ToString
public class Classroom {
    private String className = "Software Engineering 101";

    private List<Student> students;

    @Autowired
    public Classroom(List<Student> students) {
        this.students = students;
    }
}
