package com.example.demo_spring_batch.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonaDTO {
    private long id;
    private String nombre, apellidos, correo, sexo, ip;

    public PersonaDTO() {
    }
}
