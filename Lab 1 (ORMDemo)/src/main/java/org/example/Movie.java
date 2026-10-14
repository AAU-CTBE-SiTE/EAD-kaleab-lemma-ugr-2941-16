package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Movie {
    @Id
    private int Id;

    private String title;
    private String genre;
    private int releaseYear;
}
