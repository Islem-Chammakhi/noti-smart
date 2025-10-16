package com.isimm.suivi_note.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "student_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AverageSubject> averageSubject;

    @OneToMany(mappedBy = "student_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mark> marks;

    @OneToMany(mappedBy = "student_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;

}
