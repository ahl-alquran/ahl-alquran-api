package com.ahl.alquran.controller;

import com.ahl.alquran.dto.LevelStudentCount;
import com.ahl.alquran.dto.StudentResponseDTO;
import com.ahl.alquran.service.GenericExcelExportService;
import com.ahl.alquran.service.LevelStatisticsService;
import com.ahl.alquran.service.StudentExcelExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class StatisticsController {

    private final LevelStatisticsService statisticsService;
    private final GenericExcelExportService exportService;

    @GetMapping("/count/student-by-level/{year}")
    public ResponseEntity<List<LevelStudentCount>> getStudentCountByLevel(@PathVariable Integer year) {
        List<LevelStudentCount> levelStudentCounts = statisticsService.getStudentCountByLevel(year);
        return ResponseEntity.ok(levelStudentCounts);
    }

    @GetMapping("/students-by-level")
    public ResponseEntity<List<StudentResponseDTO>> getStudentByLevel(@RequestParam String level, @RequestParam Integer year) {
        List<StudentResponseDTO> studentDTOS = statisticsService.getStudentByLevel(level, year);
        return ResponseEntity.ok(studentDTOS);
    }

    @GetMapping("/excel")
    public ResponseEntity<Resource> exportStudentByLevelToExcel(@RequestParam String level, @RequestParam Integer year) {
        List<StudentResponseDTO> students = statisticsService.getStudentByLevel(level, year);
        ByteArrayOutputStream outputStream = exportService.export(students, new StudentExcelExporter(level));
        return getResourceResponse(outputStream);
    }

    @GetMapping("/excel/all")
    public ResponseEntity<Resource> exportStudentToExcel(@RequestParam Integer year) {
        List<StudentResponseDTO> students = statisticsService.getStudentByYear(year);
        ByteArrayOutputStream outputStream = exportService.export(students, new StudentExcelExporter("all"));
        return getResourceResponse(outputStream);
    }

    private ResponseEntity<Resource> getResourceResponse(ByteArrayOutputStream outputStream) {
        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        String safeFilename = "students_export_" + System.currentTimeMillis() + ".xlsx";
        String contentDisposition = "attachment; filename=\"" + safeFilename + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
