package com.isimm.suivi_note.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isimm.suivi_note.dto.StudentDto;
import com.isimm.suivi_note.models.Admin;

import com.isimm.suivi_note.repositories.AdminRepo;
import com.isimm.suivi_note.services.AdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

       private final AdminService adminService;
    private final AdminRepo adminRepo;
     @PostMapping("/")
    public ResponseEntity<Admin> addStudent(@Valid @RequestBody StudentDto dto) {
        
        
       return ResponseEntity.ok(adminService.addAdmin(dto));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<Admin>> getGetStudent () {
        return ResponseEntity.ok(adminRepo.findAll());
    }
}
