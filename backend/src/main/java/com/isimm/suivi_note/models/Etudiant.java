package com.isimm.suivi_note.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@AllArgsConstructor 
@DiscriminatorValue("ETUDIANT")
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class Etudiant extends User {

    // @Column(nullable = false, unique = true)
    // private String registrationNumber;

    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MoyenneMatiere> averageSubject;

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;


    // TODO: Change this to nullable = false and remove null values in DB
    /* TODO: Might include a service that returns Carte Etudiant
        That way, We won't need physical cards to prove we're students
        We just display information in the android app in the layout of
        Carte Étudiant + QrCode AND BOOM
    */
    @Column(unique = true)
    private String nce;
}
