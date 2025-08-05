package com.ahl.alquran.service;

import com.ahl.alquran.exception.BusinessException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class GenericExcelExportService {

    public <T> ByteArrayOutputStream export(List<T> data, ExcelExporter<T> exporter) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(exporter.getSheetName());
            sheet.setRightToLeft(true);
            // Create and apply header style
            CellStyle headerStyle = createHeaderStyle(workbook);
            // Header
            Row headerRow = sheet.createRow(0);
            String[] headers = exporter.getHeaders();
            for (int col = 0; col < headers.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col]);
                cell.setCellStyle(headerStyle);
            }
            // Data
            int rowIdx = 1;
            for (T item : data) {
                Row row = sheet.createRow(rowIdx++);
                exporter.fillData(row, item);
            }
            workbook.write(out);
            return out;
        } catch (IOException e) {
            throw new BusinessException("Excel export failed");
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }
}