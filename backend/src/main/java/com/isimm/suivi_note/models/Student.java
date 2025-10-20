package com.isimm.suivi_note.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@AllArgsConstructor 
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
    private Filiere filiere;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AverageSubject> averageSubject;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mark> marks;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;

    //TODO: Change this to nullable = false and remove null values in DB
    @Column(unique = true)
    private String nce;
}
