package com.isimm.suivi_note.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.isimm.suivi_note.dto.StudentDto;
import com.isimm.suivi_note.services.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
public class StudentController {
    private final UserService userService;
    @PostMapping("/")
    public ResponseEntity<?> addStudent(@Valid @RequestBody StudentDto dto) {
        
        
        return ResponseEntity.ok(userService.addStudent(dto));
    }
    
}
