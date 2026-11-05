package org.example.springcore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String city;
    private String country;
}
