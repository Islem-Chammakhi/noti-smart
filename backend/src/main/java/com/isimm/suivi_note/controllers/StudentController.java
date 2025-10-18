package com.isimm.suivi_note.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.isimm.suivi_note.dto.StudentDto;
import com.isimm.suivi_note.models.Student;
import com.isimm.suivi_note.repositories.StudentRepo;
import com.isimm.suivi_note.services.StudentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;

import java.util.List;

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



@RestController
@RequestMapping("/api/student")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final StudentRepo studentRep;
    @PostMapping("/")
    public ResponseEntity<?> addStudent(@Valid @RequestBody StudentDto dto) {
        
        
        return ResponseEntity.ok(studentService.addStudent(dto));
    }
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/")
    public ResponseEntity<List<Student>> getGetStudent () {
        return ResponseEntity.ok(studentRep.findAll());
    }
    
}
