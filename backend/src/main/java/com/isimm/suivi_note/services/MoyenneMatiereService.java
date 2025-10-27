package com.isimm.suivi_note.services;

import java.util.List;

import com.isimm.suivi_note.models.Matiere;
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
        return moyenneMatiereRepo.findByStudent(etudiant).orElseThrow(()-> new RuntimeException("averages not prepared yet !"));
    }

    public MoyenneMatiere addAverage(Etudiant etudiant, Matiere matiere, double average){
        return MoyenneMatiere.builder()
                                         .valeur(average)
                                         .etudiant(etudiant)
                                         .matiere(matiere)
                                         .build();

    }

    public void saveBatchAverage(List<MoyenneMatiere> moyenneList){
        moyenneMatiereRepo.saveAll(moyenneList);
    }
}
