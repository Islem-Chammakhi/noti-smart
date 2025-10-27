package com.isimm.suivi_note.models;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
public class Matiere {

    @EmbeddedId
    private MatierePK id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private double coefficient;


    @MapsId("ueId")
    @ManyToOne
    private UniteEnseignement ue;

    @OneToMany(mappedBy = "matiere")
    private List<Note> noteList;

    @ManyToMany
    @JoinTable(
            name = "matiere_typeeval",
            joinColumns = @JoinColumn(name = "matiere_id"),
            inverseJoinColumns = @JoinColumn(name = "type_eval_id")
    )
    private Set<TypeEvaluation> allowedEvals;
}
