package com.isimm.suivi_note.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.isimm.suivi_note.dto.StudentDto;
import com.isimm.suivi_note.models.Admin;

import com.isimm.suivi_note.repositories.AdminRepo;
import com.isimm.suivi_note.services.auth.AdminService;
import com.isimm.suivi_note.services.ExcelImportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final AdminRepo adminRepo;
    private final ExcelImportService excelImportService;
     @PostMapping("/")
    public ResponseEntity<Admin> addStudent(@Valid @RequestBody StudentDto dto) {
        
        
       return ResponseEntity.ok(adminService.addAdmin(dto));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<Admin>> getGetStudent () {
        return ResponseEntity.ok(adminRepo.findAll());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/upload_marks")
    public ResponseEntity<String> uploadMarks(@RequestParam("file") MultipartFile file) {
        try {
            excelImportService.importExcel(file);
            return ResponseEntity.ok("Marks uploaded successfully in db ! ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file : " + e.getMessage());
        }
    }
    
}
