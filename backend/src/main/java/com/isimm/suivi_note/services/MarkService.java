package com.isimm.suivi_note.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.isimm.suivi_note.dto.EvaluationMarkDTO;
import com.isimm.suivi_note.dto.SubjectMarkResponseDTO;
import com.isimm.suivi_note.models.Mark;
import com.isimm.suivi_note.models.Student;
import com.isimm.suivi_note.models.Subject;
import com.isimm.suivi_note.models.SubjectEvalType;
import com.isimm.suivi_note.repositories.MarkRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarkService {

    private final MarkRepo markRepo;
    private final AverageSubjectService averageSubjectService;
    // @Transactional
    public void addMark(double mark,Student student,SubjectEvalType subjectEvalType){
        
        Mark m = Mark.builder()
                     .mark(mark)
                     .student(student)
                     .subjectEvalType(subjectEvalType)
                     .build();
        markRepo.save(m);

        calculateAndSaveAverageIfComplete(m);

    }

    private void calculateAndSaveAverageIfComplete(Mark mark){
        Student student =(Student) mark.getStudent();
        System.out.println("étudaint est : "+student);
        System.out.println("---------------------------");
        Subject subject = mark.getSubjectEvalType().getSubject(); 

        List<Mark> marks= markRepo.findByStudentCinAndSubjectId(student.getCin(), subject.getId());

        if (marks.size()==3){
            double average = 0;
            for (Mark m : marks){
                average += m.getMark()*m.getSubjectEvalType().getEvalType().getCoefficient();
            }
            averageSubjectService.addAverage(student, subject, average);
            
        }
    }

    public List<SubjectMarkResponseDTO> getMarksByStudent(Student student){
        List<Mark> marks = markRepo.findByStudent(student);
        if(marks.isEmpty()){
               return List.of();
        }

        Map<String, List<Mark>> marksBySubject = marks.stream()
        .collect(Collectors.groupingBy(
            m -> m.getSubjectEvalType().getSubject().getNom()
        ));                               
        
        List<SubjectMarkResponseDTO> subjectMarkResponseDTOs = marksBySubject.entrySet().stream().map(entry -> {
            List<EvaluationMarkDTO> evaluations = entry.getValue().stream()
                .map(mark -> EvaluationMarkDTO.builder()
                        .label(mark.getSubjectEvalType().getEvalType().getLabel())
                        .mark(mark.getMark())
                        .build())
                .collect(Collectors.toList());

            return SubjectMarkResponseDTO.builder()
                                  .subjectName(entry.getKey())
                                  .evaluations(evaluations)
                                  .build();
        }).collect(Collectors.toList());
        return subjectMarkResponseDTOs;

    }

    public List<Mark> getMarksByStudentAndSubject(String studentCin,String subjectId){
        return markRepo.findByStudentCinAndSubjectId(studentCin,subjectId);
    }
}
