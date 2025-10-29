package com.isimm.suivi_note.services;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.isimm.suivi_note.models.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.isimm.suivi_note.dto.EvaluationMarkDTO;
import com.isimm.suivi_note.dto.SubjectMarkResponseDTO;
import com.isimm.suivi_note.repositories.MarkRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarkService {

    private final MarkRepo markRepo;
    private final MoyenneMatiereService moyenneMatiereService;
    // @Transactional
    public Note addMark(double mark, Etudiant etudiant, Matiere matiere, TypeEvaluation typeEval){
        
        return Note.builder()
                .valeur(mark)
                .etudiant(etudiant)
                .matiere(matiere)
                .typeEvaluation(typeEval)
                .build();
    }
    @Transactional
    public void addBatchMark(List<Note>noteList){
        try {
            markRepo.saveAll(noteList);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Certaines notes existent déjà ou violent une contrainte d'intégrité");
        } catch (Exception e) {
            throw new RuntimeException("Erreur inattendue lors de l'ajout des notes");
        }   
        moyenneMatiereService.saveBatchAverage(
                noteList.stream()
                    .map(this::calculateAndSaveAverageIfComplete)
                    .filter(Objects::nonNull)
                    .toList()
        );

    }

    private MoyenneMatiere calculateAndSaveAverageIfComplete(Note note){
        Etudiant etudiant = note.getEtudiant();
        Matiere matiere = note.getMatiere();

        List<Note> notes = markRepo.findByEtudiantAndMatiere(etudiant, matiere);

        //TODO: Why size == 3??
        if (notes.size()==3){
            double average = 0;
            for (Note m : notes){
                average += m.getValeur()*m.getTypeEvaluation().getCoefficient();
            }
            return moyenneMatiereService.addAverage(etudiant, matiere, average);

        }
        return null;
    }

    public List<SubjectMarkResponseDTO> getMarksByStudent(Etudiant etudiant){
        List<Note> notes = markRepo.findByEtudiant(etudiant);
        if(notes.isEmpty()){
               return List.of();
        }

        Map<String, List<Note>> marksBySubject = notes.stream()
        .collect(Collectors.groupingBy(
            m -> m.getMatiere().getNom()
        ));

        return marksBySubject.entrySet().stream().map(entry -> {
            List<EvaluationMarkDTO> evaluations = entry.getValue().stream()
                .map(mark -> EvaluationMarkDTO.builder()
                        .label(mark.getTypeEvaluation().getLabelle())
                        .mark(mark.getValeur())
                        .build())
                .collect(Collectors.toList());

            return SubjectMarkResponseDTO.builder()
                                  .subjectName(entry.getKey())
                                  .evaluations(evaluations)
                                  .build();
        }).collect(Collectors.toList());

    }

    public List<Note> getNotesByEtudiantId(String cin){
        return markRepo.findByEtudiantCin(cin)
                .orElseThrow(() -> new EntityNotFoundException("Notes de l'étudiant cin=" + cin + " n'éxiste pas"));
    }
    public List<Note> getMarksByStudentAndSubject(String studentCin, String subjectId){
        return markRepo.findByStudentCinAndSubjectId(studentCin,subjectId);
    }
}
