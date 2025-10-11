package com.isimm.suivi_note.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="user_type")
@NoArgsConstructor
@AllArgsConstructor 
@Data
@SuperBuilder
@Table(name = "t_user")
public class User {
    
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
    @NotBlank(message = "password is missing")
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role ;

}
