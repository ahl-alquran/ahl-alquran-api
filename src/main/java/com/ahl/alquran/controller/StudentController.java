package com.ahl.alquran.controller;

import com.ahl.alquran.dto.StudentDTO;
import com.ahl.alquran.dto.StudentResponseDTO;
import com.ahl.alquran.entity.StudentLevelDetail;
import com.ahl.alquran.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<String> registerStudent(@RequestBody StudentDTO studentDTO) {
        StudentLevelDetail savedStudent = studentService.registerStudent(studentDTO);
        if (savedStudent.getId() > 0) {
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

    @GetMapping("/by-year")
    public ResponseEntity<Page<StudentResponseDTO>> getStudentsByYear(
            @RequestParam Integer year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "result") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        Page<StudentResponseDTO> result = studentService.getStudentsByYear(
                year, page, size, sortBy, direction);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-year/search")
    public ResponseEntity<Page<StudentResponseDTO>> searchStudentsByYear(
            @RequestParam Integer year,
            @RequestParam String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "result") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        Page<StudentResponseDTO> result = studentService.searchStudentsByYear(
                year, search, page, size, sortBy, direction);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteStudent(Integer code) {
        studentService.deleteStudent(code);
        return ResponseEntity.ok("Student deleted successfully");
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDTO studentDTO) {
        studentService.updateStudent(studentDTO);
        return ResponseEntity.ok("Student deleted successfully");
    }

}
