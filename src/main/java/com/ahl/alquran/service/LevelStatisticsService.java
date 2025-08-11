package com.ahl.alquran.service;

import com.ahl.alquran.dto.LevelStudentCount;
import com.ahl.alquran.dto.StudentResponseDTO;
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

    public List<StudentResponseDTO> getStudentByLevel(String level, Integer year) {
        return studentLevelRepository.findByLevelNameAndYear(level, year);
    }

    public List<StudentResponseDTO> getStudentByYear(Integer year) {
        return studentLevelRepository.findAllByYear(year);
    }

    public StudentResponseDTO getResult(Integer code, Integer year) {
        return studentLevelRepository.findStudentResultByCode(code, year);
    }
}
