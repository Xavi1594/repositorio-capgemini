package com.example.catalogo.domains.entities.models;

import com.example.catalogo.domains.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Actor", description = "Actor's data")
public class ActorDTO {
    @JsonProperty("id")
    @Schema(description = "Actor's id", example = "1")
    private int actorId;
    @JsonProperty("nombre")
    private String firstName;
    @JsonProperty("apellidos")
    private String lastName;

    public static ActorDTO from(Actor source) {
        return new ActorDTO(
                source.getActorId(),
                source.getFirstName(),
                source.getLastName());
    }

    public static Actor from(ActorDTO source) {
        return new Actor(
                source.getActorId(),
                source.getFirstName(),
                source.getLastName());
    }
}