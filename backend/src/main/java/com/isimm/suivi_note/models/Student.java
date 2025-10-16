package com.isimm.suivi_note.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("STUDENT")
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class Student extends User {

    // @Column(nullable = false, unique = true)
    // private String registrationNumber;

    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere_id;

}
