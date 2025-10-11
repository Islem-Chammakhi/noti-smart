package com.isimm.suivi_note.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor 
@Data
@DiscriminatorValue("STUDENT")
@SuperBuilder
public class Student extends User {

    @Column(nullable = false, unique = true)
    private String registrationNumber;
}
