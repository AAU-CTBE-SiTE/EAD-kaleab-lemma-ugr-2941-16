package org.example.springcore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@ToString
@AllArgsConstructor
public class Student {
    private int id;
    private String name;
    private int age;

    private Address address;

    @Autowired
    public Student(Address address) {
        this.address = address;
    }
}