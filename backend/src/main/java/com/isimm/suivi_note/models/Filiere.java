package com.isimm.suivi_note.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class Filiere {

    @Id
    private String id;
    @Column
    private String nom; // Ing Info

    @Column
    private int niveau; // 1, 2 ,3 ...

    @OneToMany(mappedBy = "filiere")
    private List<UniteEnseignement> ueList;

    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Etudiant> etudiants;
}
