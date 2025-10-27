package com.isimm.suivi_note.repositories;

import com.isimm.suivi_note.models.Matiere;
import com.isimm.suivi_note.models.MatierePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface MatiereRepo extends JpaRepository<Matiere, MatierePK>{
    @Query("""
                SELECT m
                FROM Matiere m
                LEFT JOIN FETCH m.allowedEvals e 
                WHERE m.id.matiereId= :matiereId AND e.labelle = :evalType
    """)
    //TODO: This returns List<Matiere> AND NOT Matiere
    Optional<Matiere> findByIdAndAllowedEval(String matiereId, String evalType);

    @Query("""
            SELECT m
            FROM Matiere m
            WHERE m.ue.id.filiereId = :filiereId
    """)
    Optional<List<Matiere>> findByFiliere(String filiereId);
}
