package com.ahl.alquran.service;

import com.ahl.alquran.dto.StudentResponseDTO;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;

@AllArgsConstructor
public class StudentExcelExporter implements ExcelExporter<StudentResponseDTO> {
    private String level;

    @Override
    public String[] getHeaders() {
        return new String[]{"الأسم", "الكود", "المستوى", "النتيجة", "البلد", "السنة"};
    }

    @Override
    public void fillData(Row row, StudentResponseDTO student) {
        row.createCell(0).setCellValue(student.getName());
        row.createCell(1).setCellValue(student.getCode());
        row.createCell(2).setCellValue(student.getLevel());
        row.createCell(3).setCellValue(student.getResult());
        row.createCell(4).setCellValue(student.getCity());
        row.createCell(5).setCellValue(student.getYear());
    }

    @Override
    public String getSheetName() {
        return level;
    }

}
