package com.ahl.alquran.repo;

import com.ahl.alquran.dto.LevelStudentCount;
import com.ahl.alquran.dto.StudentResponseDTO;
import com.ahl.alquran.entity.Level;
import com.ahl.alquran.entity.Student;
import com.ahl.alquran.entity.StudentLevelDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentLevelDetailRepository extends JpaRepository<StudentLevelDetail, Long> {


    boolean existsByStudentAndLevelAndYear(Student student, Level level, Integer year);

    List<StudentLevelDetail> findByStudentIdOrderByYearDesc(Long studentId);

    @Query("SELECT l.name as levelName, COUNT(sld.student) as studentCount " +
            "FROM StudentLevelDetail sld JOIN sld.level l " +
            "WHERE sld.year = :year " +
            "GROUP BY l.name ORDER BY studentCount DESC")
    List<LevelStudentCount> countStudentsByLevel(@Param("year") Integer year);

    @Query("SELECT NEW com.ahl.alquran.dto.StudentResponseDTO(" +
            "s.name, s.code,l.name, sl.result, s.city.name, sl.year, s.nationalId) " +
            "FROM StudentLevelDetail sl " +
            "JOIN sl.student s " +
            "JOIN sl.level l " +
            "WHERE sl.year = :year")
    Page<StudentResponseDTO> findStudentsByYear(
            @Param("year") Integer year,
            Pageable pageable);

    @Query("SELECT NEW com.ahl.alquran.dto.StudentResponseDTO(" +
            "s.name, s.code,l.name, sl.result, s.city.name, sl.year, s.nationalId) " +
            "FROM StudentLevelDetail sl " +
            "JOIN sl.student s " +
            "JOIN sl.level l " +
            "WHERE sl.year = :year AND s.name LIKE %:search% ")
    Page<StudentResponseDTO> findStudentsByYearWithSearch(
            @Param("year") Integer year,
            @Param("search") String search,
            Pageable pageable);

    StudentLevelDetail findByStudentAndYear(Student student, int year);

}
