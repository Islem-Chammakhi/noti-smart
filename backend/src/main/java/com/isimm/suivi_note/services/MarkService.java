package com.isimm.suivi_note.services;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.isimm.suivi_note.brevo.entities.BrevoNoteTemplate;
import com.isimm.suivi_note.dto.notification.NoteDTO;
import com.isimm.suivi_note.exceptions.NoteExistException;
import com.isimm.suivi_note.fcm.service.FcmService;
import com.isimm.suivi_note.models.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.isimm.suivi_note.dto.EvaluationMarkDTO;
import com.isimm.suivi_note.dto.SubjectMarkResponseDTO;
import com.isimm.suivi_note.repositories.MarkRepo;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarkService {

    private final MarkRepo markRepo;
    private final MoyenneMatiereService moyenneMatiereService;
    private final NotificationService notificationService;
    private final EmailService emailService;
    private final FcmService fcmService;

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
            moyenneMatiereService.saveBatchAverage(
                    noteList.stream()
                            .map(this::calculateAndSaveAverageIfComplete)
                            .filter(Objects::nonNull)
                            .toList()
            );
        } catch (DataIntegrityViolationException e) {
            throw new NoteExistException("Certaines notes déjà existent");
        } catch (Exception e) {
            throw new RuntimeException("Erreur inattendue lors de l'ajout des notes");
        }
    }

    /** This sends Note via three ways: SSE Notification, Email Service AND Mobile Notification*/
    public void sendNote(NoteDTO noteDTO, String cin, String emailDst){
        // Through SSE Notification (Web)
        notificationService.sendNote(noteDTO, cin);

        // Through email service
        emailService.sendEmail(emailDst, new BrevoNoteTemplate(noteDTO.value(), noteDTO.typeEval().name(), noteDTO.matiere()));

        // Testing FCM
        if(emailDst.equals("med.yassine.kharrat@gmail.com"))
            fcmService.sendMobileNote(noteDTO);
    }

    private MoyenneMatiere calculateAndSaveAverageIfComplete(Note note){
        Etudiant etudiant = note.getEtudiant();
        Matiere matiere = note.getMatiere();

        //TODO: Try to add DS Securité Informatique
        List<Note> notes = markRepo.findByEtudiantAndMatiere(etudiant, matiere);
        log.debug("This is current notes, supposedly after batch save: {}", notes);
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
