package com.ahl.alquran.repo;

import com.ahl.alquran.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Long> {


    Level findByName(String level);

}
