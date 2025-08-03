package com.ahl.alquran.service;

import com.ahl.alquran.dto.LevelStudentCount;
import com.ahl.alquran.repo.StudentLevelDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LevelStatisticsService {

    private final StudentLevelDetailRepository studentLevelRepository;

    public List<LevelStudentCount> getStudentCountByLevel(int year) {
        return studentLevelRepository.countStudentsByLevel(year);
    }
}
