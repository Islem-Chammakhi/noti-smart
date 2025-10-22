package com.isimm.suivi_note.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isimm.suivi_note.dto.SubjectAverageStatsDTO;
import com.isimm.suivi_note.dto.SubjectGeneralAverageDTO;
import com.isimm.suivi_note.models.AverageSubject;
import com.isimm.suivi_note.models.Student;
import com.isimm.suivi_note.models.Subject;
import com.isimm.suivi_note.repositories.AverageSubjectRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AverageSubjectService {

    private final AverageSubjectRepo averageSubjectRepo;


    public List<AverageSubject> getAverageSubjectsByStudent(Student student){
        return averageSubjectRepo.findByStudent(student).orElseThrow(()-> new RuntimeException("averages not prepared yet !"));
    }

    public void addAverage(Student student ,Subject subject,double average){
        AverageSubject av= AverageSubject.builder()
                                         .average(average)
                                         .student(student)
                                         .subject(subject)
                                         .build();
        averageSubjectRepo.save(av);
    }

    public List<SubjectGeneralAverageDTO> getGeneralAveragesByFiliere(String filiereId) {
        return averageSubjectRepo.findGeneralAveragePerSubjectByFiliere(filiereId);
    }

    public List<SubjectAverageStatsDTO> getAverageStatsByFiliere(String filiereId) {
        return averageSubjectRepo.findAverageStatsPerSubjectByFiliere(filiereId);
    }
}
