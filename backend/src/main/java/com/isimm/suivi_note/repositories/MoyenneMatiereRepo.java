package com.isimm.suivi_note.repositories;

import com.isimm.suivi_note.dto.SubjectAverageStatsDTO;
import com.isimm.suivi_note.dto.SubjectGeneralAverageDTO;
import com.isimm.suivi_note.models.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;

import com.isimm.suivi_note.models.MoyenneMatiere;
import com.isimm.suivi_note.models.Etudiant;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface MoyenneMatiereRepo extends JpaRepository<MoyenneMatiere,Long> {
    Optional<List<MoyenneMatiere>> findByEtudiant(Etudiant etudiant);

    Optional<MoyenneMatiere> findByEtudiantAndMatiere(Etudiant etudiant, Matiere matiere);

    @Query("""
        SELECT new com.isimm.suivi_note.dto.SubjectGeneralAverageDTO(
                m.matiere.id.matiereId, m.matiere.nom, AVG(m.valeur)
        )
        FROM MoyenneMatiere m
        WHERE m.matiere.ue.filiere.id = :filiereId
        GROUP BY m.matiere.id, m.matiere.nom
        """)
    List<SubjectGeneralAverageDTO> findByFiliereId(String filiereId);

    @Query("""
            SELECT new com.isimm.suivi_note.dto.SubjectAverageStatsDTO(
                m.matiere.id.matiereId,
                m.matiere.nom,
                SUM(CASE WHEN m.valeur>=10 THEN 1 ELSE 0 END),
                SUM(CASE WHEN m.valeur<10 THEN 1 ELSE 0 END)
            )
            FROM MoyenneMatiere m
            WHERE m.matiere.ue.filiere.id = :filiereId
    """)
    List<SubjectAverageStatsDTO> findStatsByFiliereId(String filiereId);
}
