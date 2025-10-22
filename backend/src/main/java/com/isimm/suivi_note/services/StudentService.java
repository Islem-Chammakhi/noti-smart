package com.isimm.suivi_note.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isimm.suivi_note.dto.AverageSubjectResponseDTO;
import com.isimm.suivi_note.dto.EvaluationDTO;
import com.isimm.suivi_note.dto.StudentDTO;
import com.isimm.suivi_note.dto.StudentMarksBySubjectDTO;
import com.isimm.suivi_note.dto.SubjectMarkResponseDTO;
import com.isimm.suivi_note.dto.SubjectMarksDTO;
import com.isimm.suivi_note.dto.SubjectResponseDTO;
import com.isimm.suivi_note.enums.Role;
import com.isimm.suivi_note.models.AverageSubject;
import com.isimm.suivi_note.models.Filiere;
import com.isimm.suivi_note.models.Mark;
import com.isimm.suivi_note.models.Student;
import com.isimm.suivi_note.models.Subject;
import com.isimm.suivi_note.repositories.StudentRepo;
import com.isimm.suivi_note.repositories.SubjectRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepo studentRepo;
    private final MarkService markService;
    private final AverageSubjectService averageSubjectService;
    private final SubjectRepo subjectRepo;

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

    public List<StudentMarksBySubjectDTO> getStudentMarksBySubjectAndFiliere(String filiereId,String subjectId){
        List<Student> students = studentRepo.findByFiliereId(filiereId);

        return students.stream().map(student ->{
            List<Mark> marks = markService.getMarksByStudentAndSubject(student.getCin(),subjectId);

            Double ds = null, exam = null, oralOrTp = null;

            for (Mark mark:marks){
                String evalType = mark.getSubjectEvalType().getEvalType().getLabel().name();

                switch (evalType) {
                    case "DS":
                        ds = mark.getMark();
                        break;
                    case "EXAM":
                        exam = mark.getMark();
                        break;
                    case "ORAL":
                    case "TP":
                        oralOrTp = mark.getMark();
                        break;
                }    
            }
            return StudentMarksBySubjectDTO.builder()
                    .studentCin(student.getCin())
                    .studentName(student.getFirstName()+" "+student.getLastName())
                    .ds(ds)
                    .oralOrTp(oralOrTp)
                    .exam(exam)
                    .build();
        }).toList();
    }

    public List<SubjectMarksDTO> getAllMarksByStudent(String studentCin) {
    Student student = studentRepo.findByCin(studentCin)
            .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));

    // On récupère toutes les matières de la filière de cet étudiant
    List<Subject> subjects = subjectRepo.findByFiliereId(student.getFiliere().getId());

    return subjects.stream().map(subject -> {
        List<Mark> marks = markService.getMarksByStudentAndSubject(student.getCin(), subject.getId());

        Double ds = null, exam = null, oralOrTp = null;

        for (Mark mark : marks) {
            String evalType = mark.getSubjectEvalType().getEvalType().getLabel().name();
            switch (evalType) {
                case "DS":
                    ds = mark.getMark();
                    break;
                case "EXAM":
                    exam = mark.getMark();
                    break;
                case "ORAL":
                case "TP":
                    oralOrTp = mark.getMark();
                    break;
            }
        }

        return SubjectMarksDTO.builder()
                .subjectId(subject.getId())
                .subjectName(subject.getNom())
                .ds(ds)
                .exam(exam)
                .oralOrTp(oralOrTp)
                .build();
    }).toList();
}

}
