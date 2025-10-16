package com.isimm.suivi_note.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor 
@Data
@Builder
public class UserIsimm {

    @Id
    private String cin ;
    @NotBlank(message = "firstName is missing")
    private String firstName; 
    @NotBlank(message = "lastName is missing")
    private String lastName; 
    @NotBlank(message = "email is missing")
    @Email(message = " invalid email")
    @Column(unique = true, nullable = false)
    private String email;
}
