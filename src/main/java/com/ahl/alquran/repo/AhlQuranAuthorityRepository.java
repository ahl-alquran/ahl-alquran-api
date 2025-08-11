package com.ahl.alquran.repo;

import com.ahl.alquran.entity.Authority;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AhlQuranAuthorityRepository extends CrudRepository<Authority, Long> {

    Optional<Authority> findByName(String name);
    List<Authority> findAll();
}
