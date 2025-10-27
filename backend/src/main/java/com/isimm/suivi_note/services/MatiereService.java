package com.isimm.suivi_note.services;

import com.isimm.suivi_note.models.Matiere;
import com.isimm.suivi_note.repositories.MatiereRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatiereService {
    private final MatiereRepo matiereRepo;

    public List<Matiere> getMatieresByFiliere(String filiereID){
        return matiereRepo.findByFiliere(filiereID).orElseThrow(()->new EntityNotFoundException(("Les matieres de la filière "+filiereID+" n'éxiste pas")));
    }
}
