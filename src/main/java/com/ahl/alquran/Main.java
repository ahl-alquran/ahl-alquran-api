package com.ahl.alquran;

import com.ahl.alquran.dto.StudentResponseDTO;
import com.ahl.alquran.entity.Level;
import com.ahl.alquran.entity.Student;
import com.ahl.alquran.entity.StudentLevelDetail;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        String filePath = "/resources/student_2024.xlsx";
        ExcelStudentReader reader = new ExcelStudentReader();

        try {
            List<StudentResponseDTO> students = reader.readStudentsFromExcel(filePath);
            log.info("end saving students");
        } catch (Exception e) {
            System.err.println("Failed to read Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //TODO must remove after add all users
//    @GetMapping("/createStudents")
//    public ResponseEntity<List<String>> createStudent() {
//        String filePath = "/Users/mgm/Desktop/projects/ahl-alquran/ahl-alquran-api/src/main/resources/student_2024.xlsx";
//        ExcelStudentReader reader = new ExcelStudentReader();
//        List<String> names = new ArrayList<>();
//        try {
//            List<StudentResponseDTO> students = reader.readStudentsFromExcel(filePath);
//            students.stream().forEach(studentDTO -> {
//                        Optional<Student> student = studentService.findStudentByName(studentDTO.getName());
//                        if(!student.isPresent()){
//                            names.add(studentDTO.getName());
//                        }
//                studentService.registerStudent(studentDTO);
//            });
//        } catch (Exception e) {
//            System.err.println("Failed to read Excel file: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return ResponseEntity.ok(names);
//    }

    //TODO student service must be removed after add all users from excel
//    public StudentLevelDetail registerStudent(StudentResponseDTO studentDTO) {
//        Level level = levelRepository.findByName(studentDTO.getLevel());
//        Student student = studentRepository.findByName(studentDTO.getName())
//                .orElse(null);
//        if (student != null && level != null) {
//            StudentLevelDetail registration = StudentLevelDetail.builder()
//                    .student(student)
//                    .level(level)
//                    .result(studentDTO.getResult())
//                    .year(studentDTO.getYear())
//                    .build();
//            return studentLevelRepository.save(registration);
//        }
//        return null;
//    }

}
