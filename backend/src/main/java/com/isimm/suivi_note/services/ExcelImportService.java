package com.isimm.suivi_note.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelImportService {

    public void importExcel(MultipartFile file) throws IOException {
        Map<String, String> info = extractFileInfo(file.getOriginalFilename());
        System.out.println("➡️ Matière code : " + info.get("subjectId") + " | Type évaluation : " + info.get("type"));
        

            // ! Workbook détecte le  format Excel moderne (.xlsx)
        try (InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); 
            importMarks(sheet);
        }
}

    private void importMarks(Sheet sheet) {
    for (int i = 1; i <= sheet.getLastRowNum(); i++) { // on saute la première ligne (en-têtes)
        Row row = sheet.getRow(i);
        if (row == null) {System.out.println("row "+i+" is empty");continue;};

        String cin = row.getCell(5).getStringCellValue();
        String name = row.getCell(6).getStringCellValue();
        double mark = row.getCell(8).getNumericCellValue();

        System.out.println(cin + " - " + name + " : "+ mark);

    }
    
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

}
