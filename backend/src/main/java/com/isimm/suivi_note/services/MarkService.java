package com.isimm.suivi_note.services;

import org.springframework.stereotype.Service;

import com.isimm.suivi_note.models.Mark;
import com.isimm.suivi_note.models.Student;
import com.isimm.suivi_note.models.SubjectEvalType;
import com.isimm.suivi_note.repositories.MarkRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarkService {

    private final MarkRepo markRepo;

    public void addMark(double mark,Student student,SubjectEvalType subjectEvalType){
        Mark m = Mark.builder()
                     .mark(mark)
                     .student_id(student)
                     .subject_eval_type_id(subjectEvalType)
                     .build();
        markRepo.save(m);
    }
}
