package com.isimm.suivi_note.services;

import java.util.List;

import com.isimm.suivi_note.dto.SubjectAverageStatsDTO;
import com.isimm.suivi_note.dto.SubjectGeneralAverageDTO;
import com.isimm.suivi_note.models.Matiere;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.isimm.suivi_note.models.MoyenneMatiere;
import com.isimm.suivi_note.models.Etudiant;
import com.isimm.suivi_note.repositories.MoyenneMatiereRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MoyenneMatiereService {

    private final MoyenneMatiereRepo moyenneMatiereRepo;

    public List<MoyenneMatiere> getAverageSubjectsByStudent(Etudiant etudiant){
        return moyenneMatiereRepo.findByEtudiant(etudiant).orElseThrow(()-> new RuntimeException("averages not prepared yet !"));
    }

    public MoyenneMatiere addAverage(Etudiant etudiant, Matiere matiere, double average){
        return MoyenneMatiere.builder()
                                         .valeur(average)
                                         .etudiant(etudiant)
                                         .matiere(matiere)
                                         .build();

    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void saveBatchAverage(List<MoyenneMatiere> moyenneList){

        System.out.println("Going to save "+ moyenneList.size()+" moyennes");
        moyenneMatiereRepo.saveAll(moyenneList);
    }


    public List<SubjectGeneralAverageDTO> getGeneralAveragesByFiliere(String filiereId) {
        return moyenneMatiereRepo.findByFiliereId(filiereId);
    }

    public List<SubjectAverageStatsDTO> getAverageStatsByFiliere(String filiereId) {
        return moyenneMatiereRepo.findStatsByFiliereId(filiereId);
    }
}
