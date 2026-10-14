package org.example;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Movie movie = new Movie();
        movie.setTitle("The Matrix");
        movie.setGenre("Sci-Fi");
        movie.setReleaseYear(1996);

        Configuration config = new Configuration().configure();
        SessionFactory sessionFactory = config.buildSessionFactory();

        Session session = sessionFactory.openSession();

        // transaction
        session.beginTransaction();
        session.persist(movie);

        // commit
        session.getTransaction().commit();
    }
}