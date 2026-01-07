package com.ahl.alquran.repo;

import com.ahl.alquran.dto.LevelStudentCount;
import com.ahl.alquran.dto.StudentResponseDTO;
import com.ahl.alquran.entity.Level;
import com.ahl.alquran.entity.Student;
import com.ahl.alquran.entity.StudentLevelDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentLevelDetailRepository extends JpaRepository<StudentLevelDetail, Long> {


    boolean existsByStudentAndYear(Student student, Integer year);

    boolean existsByStudentAndLevelAndYear(Student student, Level level, Integer year);

    @Query("SELECT l.name as levelName, COUNT(sld.student) as studentCount " +
            "FROM StudentLevelDetail sld JOIN sld.level l " +
            "WHERE sld.year = :year " +
            "GROUP BY l.name ORDER BY studentCount DESC")
    List<LevelStudentCount> countStudentsByLevel(@Param("year") Integer year);

    Optional<StudentLevelDetail> findByStudentCodeAndYear(Integer code, Integer year);

    @Query("SELECT NEW com.ahl.alquran.dto.StudentResponseDTO(" +
            "s.name, s.code,l.name, sl.result, s.city.name, sl.year, s.nationalId) " +
            "FROM StudentLevelDetail sl " +
            "JOIN sl.student s " +
            "JOIN sl.level l " +
            "WHERE sl.year = :year AND l.name = :level")
    List<StudentResponseDTO> findByLevelNameAndYear(String level, Integer year);

    @Query("SELECT NEW com.ahl.alquran.dto.StudentResponseDTO(" +
            "s.name, s.code,l.name, sl.result, s.city.name, sl.year, s.nationalId) " +
            "FROM StudentLevelDetail sl " +
            "JOIN sl.student s " +
            "JOIN sl.level l " +
            "WHERE sl.year = :year")
    List<StudentResponseDTO> findAllByYear(Integer year);
    @Query("SELECT NEW com.ahl.alquran.dto.StudentResponseDTO(" +
            "s.name, s.code,l.name, sl.result, s.city.name, sl.year, s.nationalId) " +
            "FROM StudentLevelDetail sl " +
            "JOIN sl.student s " +
            "JOIN sl.level l " +
            "WHERE s.code = :code AND sl.year = :year")
    StudentResponseDTO findStudentResultByCode(Integer code, Integer year);
}
