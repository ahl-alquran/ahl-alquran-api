package com.ahl.alquran;

import com.ahl.alquran.dto.StudentDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelStudentReader {

    public List<StudentDTO> readStudentsFromExcel(String filePath) throws IOException {
        List<StudentDTO> students = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            // Skip header row by starting from 1
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                try {
                    StudentDTO studentDTO = createStudentFromRow(row, dataFormatter);
                    if (studentDTO != null) {
                        students.add(studentDTO);
                    }
                } catch (Exception e) {
                    System.err.printf("Error parsing row %d: %s%n",
                            row.getRowNum() + 1, e.getMessage());
                }
            }
        }
        return students;
    }

    private StudentDTO createStudentFromRow(Row row, DataFormatter dataFormatter) {
        // Using DataFormatter to handle all cell types properly
        String name = getCellValue(row.getCell(0), dataFormatter);     // الأسم
        String level = getCellValue(row.getCell(1), dataFormatter);    // المستوى
        int score = parseScore(getCellValue(row.getCell(2), dataFormatter)); // النتيجة
        String city = getCellValue(row.getCell(3), dataFormatter);     // المدينة

        return StudentDTO.builder().name(name)
                .level(level)
                //.result(score)
                .city(city).year(1446).build();
    }

    private String getCellValue(Cell cell, DataFormatter formatter) {
        return cell != null ? formatter.formatCellValue(cell).trim() : "";
    }

    private int parseScore(String scoreValue) {
        try {
            // Handle Arabic numerals if present
            return Integer.parseInt(scoreValue.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid score value: " + scoreValue);
        }
    }
}
