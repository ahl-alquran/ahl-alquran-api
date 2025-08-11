package com.ahl.alquran;

import com.ahl.alquran.dto.StudentDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        String filePath = "/Users/mgm/Desktop/projects/competition-app/ahl-alquran-backend/src/main/resources/students.xlsx";
        ExcelStudentReader reader = new ExcelStudentReader();

        try {
            List<StudentDTO> students = reader.readStudentsFromExcel(filePath);
            log.info("end saving students");
        } catch (Exception e) {
            System.err.println("Failed to read Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //    @GetMapping("/createStudents")
//    public ResponseEntity<String> createStudent() {
//        String filePath = "/Users/mgm/Desktop/projects/competition-app/ahl-alquran-backend/src/main/resources/students.xlsx";
//        ExcelStudentReader reader = new ExcelStudentReader();
//        try {
//            List<StudentDTO> students = reader.readStudentsFromExcel(filePath);
//            students.stream().forEach(studentDTO -> studentService.registerStudent(studentDTO));
//        } catch (Exception e) {
//            System.err.println("Failed to read Excel file: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return ResponseEntity.ok("Students created successfully");
//    }
}
