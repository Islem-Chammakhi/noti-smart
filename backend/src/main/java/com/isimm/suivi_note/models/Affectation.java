package com.isimm.suivi_note.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Affectation {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeIntervention type;

    @ManyToOne
    @JoinColumn(name="ens_id") // Specifies fk name, for join purposes
    private Enseignant ens;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "matiere_id", referencedColumnName = "matiereId"),
            @JoinColumn(name = "ue_id", referencedColumnName = "ueId"),
            @JoinColumn(name = "filiere_id", referencedColumnName = "filiere_id")
    })
    private Matiere matiere;


}
