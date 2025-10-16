package com.isimm.suivi_note.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double mark;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // 🔹 Plusieurs moyennes concernent une même matière
    @ManyToOne
    @JoinColumn(name = "subject_eval_type_id", nullable = false)
    private SubjectEvalType subject_eval_type_id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
