package com.ahl.alquran.repo;

import com.ahl.alquran.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LevelRepository extends JpaRepository<Level, Long> {


    Level findByName(String level);

    @Query("SELECT l FROM Level l ORDER BY l.id DESC")
    List<Level> findAllOrderById();
}
