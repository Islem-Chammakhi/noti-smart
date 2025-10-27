package com.isimm.suivi_note.services;

import java.util.List;

import com.isimm.suivi_note.enums.Eval;
import com.isimm.suivi_note.models.*;
import com.isimm.suivi_note.models.MoyenneMatiere;
import org.springframework.stereotype.Service;

import com.isimm.suivi_note.dto.AverageSubjectResponseDTO;
import com.isimm.suivi_note.dto.EvaluationDTO;
import com.isimm.suivi_note.dto.StudentDTO;
import com.isimm.suivi_note.dto.StudentMarksBySubjectDTO;
import com.isimm.suivi_note.dto.SubjectMarkResponseDTO;
import com.isimm.suivi_note.dto.SubjectMarksDTO;
import com.isimm.suivi_note.dto.SubjectResponseDTO;
import com.isimm.suivi_note.enums.Role;
import com.isimm.suivi_note.models.Matiere;
import com.isimm.suivi_note.repositories.StudentRepo;
import com.isimm.suivi_note.repositories.MatiereRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepo studentRepo;
    private final MarkService markService;
    private final MoyenneMatiereService moyenneMatiereService;
    private final EvaluationService evalService;
    private final MatiereService matiereService;

    public Etudiant addStudent(StudentDTO studentDto){
        Etudiant etudiant = Etudiant.builder()
                .cin(studentDto.cin())
                .firstName(studentDto.firstName())
                .lastName(studentDto.lastName())
                .email(studentDto.email())
                .password(studentDto.password())
                // .registrationNumber(studentDto.registrationNumber())
                .role(Role.STUDENT)
                .build();
        return studentRepo.save(etudiant);
    }

    public Etudiant getStudentByCin(String cin) {
        return studentRepo.findByCin(cin).orElseThrow(()-> new RuntimeException("student with cin "+cin+" not found !"));
    }

    public List<SubjectResponseDTO> getSubjectsByStudent(String studentCin) {
        Etudiant etudiant =getStudentByCin(studentCin);

        Filiere filiere = etudiant.getFiliere();


        return filiere.getUeList().stream()
                .map(UniteEnseignement::getMatiereList)
                .flatMap(List::stream)
                .map(mat->
                     SubjectResponseDTO.builder()
                        .subjectId(mat.getId().getMatiereId()) // TODO: DO we need this?!
                        .subjectName(mat.getNom())
                        .coefficient(mat.getCoefficient())
                        .evaluations(evalService.toProjection(mat.getAllowedEvals()))
                        .build()
                ).toList();

    }

    /*public List<SubjectMarkResponseDTO> getMarks(String cin){
        Etudiant etudiant = getStudentByCin(cin);
        return markService.getMarksByStudent(etudiant);
    }
*/
    public List<AverageSubjectResponseDTO> getAverages(String cin) {
        Etudiant etudiant = getStudentByCin(cin);
        List<MoyenneMatiere> avs = moyenneMatiereService.getAverageSubjectsByStudent(etudiant);
        return avs.stream()
                .map(av -> AverageSubjectResponseDTO.builder()
                        .average(av.getValeur())
                        .subjectName(av.getMatiere().getNom())
                        .subjectId(av.getMatiere().getId().getMatiereId())
                        .build())
                .toList();
    }

    public List<StudentMarksBySubjectDTO> getStudentMarksBySubjectAndFiliere(String filiereId,String subjectId){
        List<Etudiant> etudiants = studentRepo.findByFiliereId(filiereId);

        return etudiants.stream().map(student ->{
            List<Note> notes = markService.getMarksByStudentAndSubject(student.getCin(),subjectId); // This is cool

            Double ds = null, exam = null, oralOrTp = null;

            for (Note note : notes){
                Eval evalType = note.getTypeEvaluation().getLabelle();

                switch (evalType) {
                    case Eval.DS:
                        ds = note.getValeur();
                        break;
                    case Eval.EXAM:
                        exam = note.getValeur();
                        break;
                    case Eval.ORAL:
                    case Eval.TP:
                        oralOrTp = note.getValeur();
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
    Etudiant etudiant = studentRepo.findByCin(studentCin)
            .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));



    // On récupère toutes les matières de la filière de cet étudiant
    List<Matiere> matieres = matiereService.getMatieresByFiliere(etudiant.getFiliere().getId());

    return matieres.stream().map(subject -> {
        List<Note> notes = markService.getMarksByStudentAndSubject(etudiant.getCin(), subject.getId().getMatiereId());

        Double ds = null, exam = null, oralOrTp = null;

        for (Note note : notes) {
            Eval evalType = note.getTypeEvaluation().getLabelle();
            switch (evalType) {
                case Eval.DS:
                    ds = note.getValeur();
                    break;
                case Eval.EXAM:
                    exam = note.getValeur();
                    break;
                case Eval.ORAL:
                case Eval.TP:
                    oralOrTp = note.getValeur();
                    break;
            }
        }

        return SubjectMarksDTO.builder()
                .subjectId(subject.getId().getMatiereId())
                .subjectName(subject.getNom())
                .ds(ds)
                .exam(exam)
                .oralOrTp(oralOrTp)
                .build();
    }).toList();
}

}
