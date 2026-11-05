package org.example.springcore.models;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("org.example.springcore");

        Classroom classroom = context.getBean(Classroom.class);
        System.out.println(classroom);
    }
}
