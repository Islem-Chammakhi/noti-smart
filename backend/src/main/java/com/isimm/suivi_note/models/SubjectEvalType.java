package com.isimm.suivi_note.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class SubjectEvalType {
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "eval_type", nullable = false)
    private EvaluationType eval_type;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject_id;

    @OneToMany(mappedBy = "subject_eval_type_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mark> marks;
}
