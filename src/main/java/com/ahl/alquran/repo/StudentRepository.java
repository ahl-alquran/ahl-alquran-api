package com.ahl.alquran.repo;

import com.ahl.alquran.dto.StudentHistoryDTO;
import com.ahl.alquran.dto.StudentResponseDTO;
import com.ahl.alquran.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByCode(int code);

    @Query("SELECT COUNT(DISTINCT sld.student) FROM StudentLevelDetail sld WHERE sld.year = :year")
    long countByYear(@Param("year") Integer year);

    void deleteByCode(Integer code);

    @Query("SELECT NEW com.ahl.alquran.dto.StudentResponseDTO(" +
            "s.name, s.code, s.city.name, s.nationalId) " +
            "FROM Student s ")
    Page<StudentResponseDTO> findStudents(Pageable pageable);

    @Query("SELECT NEW com.ahl.alquran.dto.StudentResponseDTO(" +
            "s.name, s.code, s.city.name, s.nationalId) " +
            "FROM Student s WHERE s.name LIKE %:search%")
    Page<StudentResponseDTO> findStudentsWithSearch(@Param("search") String search,
                                                    Pageable pageable);

    @Query("SELECT NEW com.ahl.alquran.dto.StudentHistoryDTO(" +
            "sl.year, l.name, sl.result) " +
            "FROM StudentLevelDetail sl " +
            "JOIN sl.student s " +
            "JOIN sl.level l " +
            "WHERE s.code = :code ORDER BY sl.year DESC")
    List<StudentHistoryDTO> findByStudentCodeOrderByYearDesc(Integer code);

    Optional<Student> findByName(String name);

}
