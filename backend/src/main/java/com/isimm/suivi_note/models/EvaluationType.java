package com.isimm.suivi_note.models;

import java.util.List;

import com.isimm.suivi_note.enums.EvaluationLabel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class EvaluationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EvaluationLabel label;

    @Column(nullable = false)
    private double coefficient;

    @OneToMany(mappedBy = "evalType",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SubjectEvalType> subjectEvalTypes;

}
