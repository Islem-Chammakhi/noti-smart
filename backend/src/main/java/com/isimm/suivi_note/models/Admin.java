package com.isimm.suivi_note.models;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@DiscriminatorValue("ADMIN")
@NoArgsConstructor
public class Admin extends User {

}
