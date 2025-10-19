package com.isimm.suivi_note.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.isimm.suivi_note.dto.AverageSubjectResponseDTO;
import com.isimm.suivi_note.dto.EvaluationDTO;
import com.isimm.suivi_note.dto.StudentDTO;
import com.isimm.suivi_note.dto.SubjectMarkResponseDTO;
import com.isimm.suivi_note.dto.SubjectResponseDTO;
import com.isimm.suivi_note.enums.Role;
import com.isimm.suivi_note.models.AverageSubject;
import com.isimm.suivi_note.models.Filiere;
import com.isimm.suivi_note.models.Student;
import com.isimm.suivi_note.repositories.StudentRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepo studentRepo;
    private final MarkService markService;
    private final AverageSubjectService averageSubjectService;

    public Student addStudent(StudentDTO studentDto){
        Student student = Student.builder()
                .cin(studentDto.cin())
                .firstName(studentDto.firstName())
                .lastName(studentDto.lastName())
                .email(studentDto.email())
                .password(studentDto.password())
                // .registrationNumber(studentDto.registrationNumber())
                .role(Role.STUDENT)
                .build();
        return studentRepo.save(student);
    }

    public Student getStudentByCin(String cin) {
        return studentRepo.findByCin(cin).orElseThrow(()-> new RuntimeException("student with cin "+cin+" not found !"));
    }

    public List<SubjectResponseDTO> getSubjectsByStudent(String studentCin) {
        Student student =getStudentByCin(studentCin);

        Filiere filiere = student.getFiliere();

        return filiere.getSubjects().stream().map(subject -> {
            List<EvaluationDTO> evals = subject.getSubjectEvalTypes().stream()
                .map(s -> new EvaluationDTO(
                    s.getEvalType().getLabel(),
                    s.getEvalType().getCoefficient()
                ))
                .toList();

            return SubjectResponseDTO.builder().subjectId(subject.getId()).subjectName(subject.getNom()).coefficient(subject.getCoefficient()).evaluations(evals).build();
            
        }).toList();
    }

    public List<SubjectMarkResponseDTO> getMarks(String cin){
        Student student = getStudentByCin(cin); 
        return markService.getMarksByStudent(student);
    }

    public List<AverageSubjectResponseDTO> getAverages(String cin) {
        Student student = getStudentByCin(cin);
        List<AverageSubject> avs = averageSubjectService.getAverageSubjectsByStudent(student);
        return avs.stream()
                .map(av -> AverageSubjectResponseDTO.builder()
                        .average(av.getAverage())
                        .subjectName(av.getSubject().getNom())
                        .subjectId(av.getSubject().getId())
                        .build())
                .toList();
    }
}
