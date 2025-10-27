package com.isimm.suivi_note.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UniteEnseignement {
    @EmbeddedId
    private UniteEnsPK id; // This should Become 2 primary keys (one is id and the other is filiereId

    @Column
    private String nom;

    @Column
    private int credits;

    @Column
    private int coef;


    @ManyToOne
    @MapsId("filiereId")
    private Filiere filiere; // Filiere's id should be foreign and primary key of class UniteEnseignement.

    @OneToMany(mappedBy = "ue")
    private List<Matiere> matiereList;

}
