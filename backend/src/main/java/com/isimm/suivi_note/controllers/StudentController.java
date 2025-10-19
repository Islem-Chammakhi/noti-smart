package com.isimm.suivi_note.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.isimm.suivi_note.dto.AverageSubjectResponseDTO;
import com.isimm.suivi_note.dto.StudentDTO;
import com.isimm.suivi_note.dto.SubjectMarkResponseDTO;
import com.isimm.suivi_note.dto.SubjectResponseDTO;
import com.isimm.suivi_note.models.Student;
import com.isimm.suivi_note.repositories.StudentRepo;
import com.isimm.suivi_note.services.StudentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/*
* REST Prefix: /api
* Services: AuthService, NotificationService, StudentService, UserService (we have users because we might include professors, so operations should affect any kind of users)
*   - AuthService: All operations used for authentication like forget password, login, sign in, etc
*   - UserService: Operations on all users like Archive users (make them inaccessible <=> can't log in, but exists), Add users, Delete users)
*   - StudentService: Operations regarding students (getNotes, get study plans
*   - NotificationService: houses operations regarding sending messages to user
* */
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final StudentRepo studentRep;
    @PostMapping("/")
    public ResponseEntity<?> addStudent(@Valid @RequestBody StudentDTO dto) {
        
        
        return ResponseEntity.ok(studentService.addStudent(dto));
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/")
    public ResponseEntity<List<Student>> getGetStudent () {
        return ResponseEntity.ok(studentRep.findAll());
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/{cin}/subjects")
    public  ResponseEntity<List<SubjectResponseDTO>> getSubjectsByStudent(@PathVariable String cin) {
        try{
            return ResponseEntity.ok(studentService.getSubjectsByStudent(cin));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }

    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/{cin}/marks")
    public ResponseEntity<List<SubjectMarkResponseDTO>> getMethodName(@PathVariable String cin) {
                try{
            return ResponseEntity.ok(studentService.getMarks(cin));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/{cin}/subjects/average")
    public ResponseEntity<List<AverageSubjectResponseDTO>> getAverageSubjects(@PathVariable String cin) {
        try{
            return ResponseEntity.ok(studentService.getAverages(cin));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }
    
    
    
    
}
