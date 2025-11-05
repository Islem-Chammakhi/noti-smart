package com.isimm.suivi_note.services;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.isimm.suivi_note.brevo.entities.BrevoNoteTemplate;
import com.isimm.suivi_note.dto.notification.NoteDTO;
import com.isimm.suivi_note.exceptions.NoteExistException;
/*import com.isimm.suivi_note.fcm.service.FcmService;*/
import com.isimm.suivi_note.models.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
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
/*    private final FcmService fcmService;*/

    // @Transactional
    public void addMark(double mark, Etudiant etudiant, Matiere matiere, TypeEvaluation typeEval){

        Note m = Note.builder()
                .valeur(mark)
                .etudiant(etudiant)
                .matiere(matiere)
                .typeEvaluation(typeEval)
                .build();
        try{
            markRepo.save(m);

            calculateAndSaveAverageIfComplete(m);

        }catch (DataIntegrityViolationException e){
            throw new NoteExistException("Note de l'étudiant "+etudiant.getCin()+" déjà existe");
        }
    }


    /** This sends Note via three ways: SSE Notification, Email Service AND Mobile Notification*/
    public void sendNote(NoteDTO noteDTO, String cin, String emailDst){
        // Through SSE Notification (Web)
        notificationService.sendNote(noteDTO, cin);

        // Through email service
        emailService.sendEmail(emailDst, new BrevoNoteTemplate(noteDTO.value(), noteDTO.typeEval().name(), noteDTO.matiere()));

        // Testing FCM
        /*if(emailDst.equals("med.yassine.kharrat@gmail.com"))
            fcmService.sendMobileNote(noteDTO);*/
    }

    private void calculateAndSaveAverageIfComplete(Note note){
        Etudiant etudiant = note.getEtudiant();
        Matiere matiere = note.getMatiere();

        List<Note> notes = markRepo.findByEtudiantAndMatiere(etudiant, matiere);
        System.out.println("This is current notes, supposedly after batch save: "+notes);

        if (notes.size()==3){
            double average = 0;
            for (Note m : notes){
                average += m.getValeur()*m.getTypeEvaluation().getCoefficient();
            }
            moyenneMatiereService.addAverage(etudiant, matiere, average);
            System.out.println("moyenne de l'étudiant" +etudiant.getFirstName()+" est "+average+"");

        }

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
