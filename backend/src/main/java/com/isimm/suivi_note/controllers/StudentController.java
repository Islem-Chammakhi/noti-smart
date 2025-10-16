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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/student")
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
