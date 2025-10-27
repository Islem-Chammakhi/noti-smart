package com.isimm.suivi_note.services;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.isimm.suivi_note.dto.notification.NoteDTO;
import com.isimm.suivi_note.models.Matiere;
import com.isimm.suivi_note.models.Note;
import com.isimm.suivi_note.models.TypeEvaluation;
import com.isimm.suivi_note.repositories.MatiereRepo;
import jakarta.persistence.EntityNotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.isimm.suivi_note.models.Etudiant;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelImportService {

    private final StudentService studentService;
    private final MarkService markService;
    private final NotificationService notificationService;
    private final MatiereRepo matiereRepo;

    // @Transactional
    public void importExcel(MultipartFile file) throws IOException {
        Map<String, String> info = extractFileInfo(file.getOriginalFilename());
        System.out.println("➡️ Matière code : " + info.get("subjectId") + " | Type évaluation : " + info.get("type"));
        Matiere matiere = matiereRepo.findByIdAndAllowedEval(info.get("subjectId"), info.get("type"))
                .orElseThrow(()->new EntityNotFoundException("Matiere id="+info.get("subjectId")+" n'a pas d'évaluation de type "+info.get("type")));

        TypeEvaluation type = matiere.getAllowedEvals().iterator().next();
        // ! Workbook détecte le  format Excel moderne (.xlsx)
        try (InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); 
            importMarks(sheet,matiere, type);
        }
}
    @Transactional
    private void importMarks(Sheet sheet,Matiere matiere, TypeEvaluation type) {
        System.out.println("Number of lines = "+ sheet.getLastRowNum());
        ArrayList<Note> noteList = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // on saute la première ligne (en-têtes)
            Row row = sheet.getRow(i);
            if (row == null || isRowEmpty(row)) {System.out.println("row "+i+" is empty");continue;};
            Cell cinCell = row.getCell(5);

            Cell markCell = row.getCell(7);
            String cin = cinCell.getStringCellValue();
            double mark = markCell.getNumericCellValue();

            if(markCell ==null ||cinCell==null || markCell.getCellType() != CellType.NUMERIC || cinCell.getCellType() == CellType.BLANK){
                throw new RuntimeException("invalid inputs in file xl we cannot add marks ");
            }
            Etudiant etudiant = studentService.getStudentByCin(cin);
            noteList.add(markService.addMark(mark, etudiant, matiere, type));

            NoteDTO noteDTO= NoteDTO.builder()
                        .value(mark)
                        .matiere(matiere.getNom())
                        .dateSaisie(LocalDateTime.now())
                        .typeEval(type.getLabelle())
                        .build();
            notificationService.sendNote(noteDTO, cin);
            System.out.println(cin + " - "  + " : "+ mark+" added to List");
        }
        markService.addBatchMark(noteList);
        System.out.println("Enregistré "+ noteList.size() +" notes avec succès");
    }

    
    private Map<String, String> extractFileInfo(String fileName) {
        Map<String, String> info = new HashMap<>();
        if (fileName != null && fileName.contains("_")) {
            String[] parts = fileName.split("_");
            String codeMatiere = parts[0];
            String type = parts[1].replace(".xlsx", "").replace(".xls", ""); // Retirer extension
            info.put("subjectId", codeMatiere);
            info.put("type", type.toUpperCase()); // DS, EXAM, TP...
        }
        return info;
}


    private boolean isRowEmpty(Row row) {
    for (int c = 0; c < row.getLastCellNum(); c++) {
        Cell cell = row.getCell(c);
        if (cell != null && cell.getCellType() != CellType.BLANK) {
            return false;
        }
    }
    return true;
}
}
