package com.ahl.alquran.repo;

import com.ahl.alquran.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByCode(int code);

    @Query("SELECT COUNT(DISTINCT sld.student) FROM StudentLevelDetail sld WHERE sld.year = :year")
    long countByYear(@Param("year") Integer year);

    void deleteByCode(Integer code);

}
