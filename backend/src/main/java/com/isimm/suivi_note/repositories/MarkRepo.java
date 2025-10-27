package com.isimm.suivi_note.repositories;

import com.isimm.suivi_note.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.isimm.suivi_note.models.Etudiant;
import com.isimm.suivi_note.models.Matiere;

import java.util.List;
import java.util.Optional;


public interface MarkRepo extends JpaRepository<Note,Long> {
    List<Note> findByEtudiant(Etudiant etudiant);

    @Query("SELECT m FROM Note m WHERE m.etudiant = :etudiant AND m.matiere = :matiere")
    List<Note> findByEtudiantAndMatiere(Etudiant etudiant, Matiere matiere);


    @Query("SELECT m FROM Note m WHERE m.etudiant.cin = :studentCin AND m.matiere.id.matiereId= :subjectId")
    List<Note> findByStudentCinAndSubjectId(String studentCin, String subjectId );

    @Query("SELECT n FROM Note n WHERE n.etudiant.cin = :etudiantCin")
    Optional<List<Note>> findByEtudiantCin(String etudiantCin);
}
