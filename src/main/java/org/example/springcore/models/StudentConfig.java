package org.example.springcore.models;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {
    @Bean
    public Student student1() {
        return new Student(101, "Alice", 28, new Address("Addis", "Ethiopia"));
    }

    @Bean
    public Student student2() {
        return new Student(102, "Bob", 19, new Address("Hawassa", "Ethiopia"));
    }
}
