package com.isimm.suivi_note.repositories;

import com.isimm.suivi_note.enums.Eval;
import com.isimm.suivi_note.models.Matiere;
import com.isimm.suivi_note.models.MatierePK;
import com.isimm.suivi_note.models.UniteEnsPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface MatiereRepo extends JpaRepository<Matiere, MatierePK>{
@Query("""
    SELECT m
    FROM Matiere m
    LEFT JOIN FETCH m.allowedEvals e 
    WHERE m.id.matiereId = :matiereId 
      AND m.id.ueId.ueId = :ueId 
      AND m.id.ueId.filiereId = :filiereId 
      AND e.labelle = :evalType
""")
Optional<Matiere> findByIdAndAllowedEval(
        @Param("matiereId") String matiereId,
        @Param("ueId") String ueId,
        @Param("filiereId") String filiereId,
        @Param("evalType") Eval evalType
);

    @Query("""
            SELECT m
            FROM Matiere m
            WHERE m.ue.id.filiereId = :filiereId
    """)
    Optional<List<Matiere>> findByFiliere(String filiereId);
}
