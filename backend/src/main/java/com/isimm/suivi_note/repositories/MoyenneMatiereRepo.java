package com.isimm.suivi_note.repositories;

import com.isimm.suivi_note.models.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;

import com.isimm.suivi_note.models.MoyenneMatiere;
import com.isimm.suivi_note.models.Etudiant;

import java.util.List;
import java.util.Optional;


public interface MoyenneMatiereRepo extends JpaRepository<MoyenneMatiere,Long> {
    Optional<List<MoyenneMatiere>> findByStudent(Etudiant etudiant);

    Optional<MoyenneMatiere>  findByStudentAndSubject(Etudiant etudiant, Matiere matiere);


}
