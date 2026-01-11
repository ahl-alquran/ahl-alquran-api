package com.ahl.alquran.controller;

import com.ahl.alquran.dto.*;
import com.ahl.alquran.entity.Student;
import com.ahl.alquran.entity.StudentLevelDetail;
import com.ahl.alquran.exception.BusinessException;
import com.ahl.alquran.service.LevelStatisticsService;
import com.ahl.alquran.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAuthority;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final LevelStatisticsService levelStatisticsService;

    @PostMapping("/register")
    public ResponseEntity<String> registerStudent(@RequestBody StudentDTO studentDTO) {
        Student student = studentService.registerStudent(studentDTO);
        if (student.getId() > 0) {
            return ResponseEntity.ok("Student Registration successful");
        } else {
            return ResponseEntity.badRequest().body("Student Registration failed");
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalStudentCount() {
        return ResponseEntity.ok(studentService.getTotalStudentCount());
    }

    @GetMapping("/count/by-year/{year}")
    public ResponseEntity<Long> getStudentCountByYear(@PathVariable Integer year) {
        long count = studentService.getStudentCountByYear(year);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<StudentResponseDTO>> getStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "code") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {

        Page<StudentResponseDTO> result = studentService.getStudents(
                page, size, sortBy, direction);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StudentResponseDTO>> searchStudentsByYear(
            @RequestParam String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "code") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {

        Page<StudentResponseDTO> result = studentService.searchStudents(
                search, page, size, sortBy, direction);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteStudent(Integer code) {
        studentService.deleteStudent(code);
        return ResponseEntity.ok("Student deleted successfully");
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDTO studentDTO) {
        studentService.updateStudent(studentDTO);
        return ResponseEntity.ok("Student deleted successfully");
    }

    @GetMapping("/level/list")
    public ResponseEntity<Set<LevelResponseDTO>> getAllLevels() {
        Set<LevelResponseDTO> levels = studentService.getLevels();
        return ResponseEntity.status(HttpStatus.OK).body(levels);
    }

    @GetMapping("/city/list")
    public ResponseEntity<Set<CityResponseDTO>> getAllCities() {
        Set<CityResponseDTO> levels = studentService.getCities();
        return ResponseEntity.status(HttpStatus.OK).body(levels);
    }

    @GetMapping("/history")
    public ResponseEntity<List<StudentHistoryDTO>> getStudentHistory(@RequestParam Integer code) {
        List<StudentHistoryDTO> studentHistoryList = studentService.getStudentHistory(code);
        return ResponseEntity.status(HttpStatus.OK).body(studentHistoryList);
    }

    @GetMapping("/{code}")
    public ResponseEntity<StudentResponseDTO> getStudentInfo(@PathVariable Integer code) {

        StudentResponseDTO result = studentService.getStudentByCode(code);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/exam/register")
    public ResponseEntity<String> registerStudentExam(@RequestBody StudentExamDTO studentExamDTO) {
        StudentLevelDetail savedStudent = studentService.registerStudentTest(studentExamDTO);
        if (savedStudent.getId() > 0) {
            return ResponseEntity.ok("Exam Registration successful");
        } else {
            return ResponseEntity.badRequest().body("Exam Registration failed");
        }
    }

    @GetMapping("/details")
    public ResponseEntity<StudentResponseDTO> getStudentDetails(@RequestParam Integer code, @RequestParam Integer year) {
        StudentResponseDTO result = levelStatisticsService.getResult(code, year);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        throw new BusinessException("لا توجد نتائج للطالب في هذه السنة");
    }

    @PostMapping("/update-result")
    public ResponseEntity<String> updateStudentResult(@RequestBody StudentResultDTO studentResultDTO) {
        studentService.updateStudentResult(studentResultDTO);
        return ResponseEntity.ok("Result update successfully");
    }
}
