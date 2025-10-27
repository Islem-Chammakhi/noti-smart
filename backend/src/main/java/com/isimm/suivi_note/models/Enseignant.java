package com.isimm.suivi_note.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@DiscriminatorValue("ENSEIGNANT")
@NoArgsConstructor
@Getter
@Setter
public class Enseignant extends User{
    //TODO: Dunno what to populate this class with...

    /*@OneToMany(mappedBy = "ens") // Specifies the name of the java attribute that's inside Affectation
    private List<Affectation> affectationList;*/
}
