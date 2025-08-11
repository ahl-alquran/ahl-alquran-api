package com.ahl.alquran.service;

import com.ahl.alquran.dto.*;
import com.ahl.alquran.entity.City;
import com.ahl.alquran.entity.Level;
import com.ahl.alquran.entity.Student;
import com.ahl.alquran.entity.StudentLevelDetail;
import com.ahl.alquran.repo.CityRepository;
import com.ahl.alquran.repo.LevelRepository;
import com.ahl.alquran.repo.StudentLevelDetailRepository;
import com.ahl.alquran.repo.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final LevelRepository levelRepository;
    private final StudentLevelDetailRepository studentLevelRepository;
    private final CityRepository cityRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public Student registerStudent(StudentDTO studentDTO) {
        studentRepository.findByName(studentDTO.name()).ifPresent(s -> {
            throw new IllegalStateException("Student already exists");
        });
        City city = cityRepository.findByName(studentDTO.city());
        Student student = Student.builder()
                .name(studentDTO.name())
                .code(getNextSequenceValue())
                .city(city)
                .nationalId(studentDTO.nationalId())
                .build();
        return studentRepository.save(student);
    }

    public StudentLevelDetail registerStudentTest(StudentExamDTO studentExamDTO) {

        Student student = studentRepository.findByCode(studentExamDTO.code())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with this code"));
        Level level = levelRepository.findByName(studentExamDTO.level());
        if(level == null){
            throw new EntityNotFoundException("Level not found");
        }
        // Check if already registered
        int year = studentExamDTO.year();
        if (studentLevelRepository.existsByStudentAndLevelAndYear(student, level, year)) {
            throw new IllegalStateException("Student already registered for this level/year");
        }

        StudentLevelDetail registration = StudentLevelDetail.builder()
                .student(student)
                .level(level)
                .year(year)
                .build();
        return studentLevelRepository.save(registration);
    }

    public StudentLevelDetail updateResult(Long registrationId, int result) {
        StudentLevelDetail registration = studentLevelRepository.findById(registrationId)
                .orElseThrow(() -> new EntityNotFoundException("Registration not found"));

        registration.setResult(result);
        return studentLevelRepository.save(registration);
    }

    public Long getTotalStudentCount() {
        return studentRepository.count();
    }

    public Long getStudentCountByYear(int year) {
        return studentRepository.countByYear(year);
    }

    public Page<StudentResponseDTO> getStudents(int page, int size,
                                                String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return studentRepository.findStudents(pageable);
    }

    public Page<StudentResponseDTO> searchStudents(String searchTerm,
                                                   int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return studentRepository.findStudentsWithSearch(searchTerm,
                pageable);
    }

    private Integer getNextSequenceValue() {
        Query query = entityManager.createNativeQuery(
                "SELECT nextval('student_code_seq')");
        return ((Number) query.getSingleResult()).intValue();
    }

    public void updateStudent(StudentDTO studentDTO) {
        Student student = studentRepository.findByCode(studentDTO.code())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        student.setName(studentDTO.name());
        student.setNationalId(studentDTO.nationalId());
        student.setCity(cityRepository.findByName(studentDTO.city()));
        studentRepository.save(student);
    }

    public void deleteStudent(Integer code) {
        studentRepository.deleteByCode(code);
    }


    public Set<LevelResponseDTO> getLevels() {
        return levelRepository.findAllOrderById().stream()
                .map(level -> new LevelResponseDTO(level.getName()))
                .collect(Collectors.toSet());
    }

    public Set<CityResponseDTO> getCities() {
        return cityRepository.findAll().stream()
                .map(level -> new CityResponseDTO(level.getName()))
                .collect(Collectors.toSet());
    }

    public List<StudentHistoryDTO> getStudentHistory(Integer code) {
        return studentRepository.findByStudentCodeOrderByYearDesc(code);
    }

    public StudentResponseDTO getStudentByCode(Integer code) {
        return studentRepository.findByCode(code)
                .map(student -> new StudentResponseDTO(
                        student.getName(),
                        student.getCode(),
                        student.getCity().getName(),
                        student.getNationalId()))
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    public Optional<Student> findStudentByName(String name) {
        return studentRepository.findByName(name);
    }

}
