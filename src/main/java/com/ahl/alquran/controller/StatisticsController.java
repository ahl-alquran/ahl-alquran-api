package com.ahl.alquran.controller;

import com.ahl.alquran.dto.LevelStudentCount;
import com.ahl.alquran.dto.StudentDTO;
import com.ahl.alquran.entity.StudentLevelDetail;
import com.ahl.alquran.service.LevelStatisticsService;
import com.ahl.alquran.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class StatisticsController {

    private final LevelStatisticsService statisticsService;

    @GetMapping("/count/student-by-level/{year}")
    public ResponseEntity<List<LevelStudentCount>> getStudentCountByLevel(@PathVariable Integer year) {
        List<LevelStudentCount> levelStudentCounts = statisticsService.getStudentCountByLevel(year);
        return ResponseEntity.ok(levelStudentCounts);
    }

}
