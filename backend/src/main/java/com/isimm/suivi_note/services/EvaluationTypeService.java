package com.isimm.suivi_note.services;

import org.springframework.stereotype.Service;

import com.isimm.suivi_note.repositories.EvaluationTypeRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EvaluationTypeService {

    private final EvaluationTypeRepo evaluationTypeRepo;

    
}
