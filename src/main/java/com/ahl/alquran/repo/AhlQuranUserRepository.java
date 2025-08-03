package com.ahl.alquran.repo;

import com.ahl.alquran.entity.AhlQuranUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AhlQuranUserRepository extends CrudRepository<AhlQuranUser, Long> {

    Optional<AhlQuranUser> findByUsername(String username);
}
