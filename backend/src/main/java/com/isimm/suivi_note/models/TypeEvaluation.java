package com.isimm.suivi_note.models;


import java.util.List;

import com.isimm.suivi_note.enums.Eval;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeEvalId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Eval labelle;

    @Column(nullable = false)
    private double coefficient;

    /* Do I need this? */
    @OneToMany(mappedBy = "typeEvaluation")
    private List<Note> noteList;

    /* I don't see we need this anytime soon */
    /*@ManyToMany(mappedBy = "allowed_evals")
    private Set<Matiere> matiereSet;*/
}
