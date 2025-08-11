package com.ahl.alquran.repo;

import com.ahl.alquran.dto.UserResponseDTO;
import com.ahl.alquran.entity.AhlQuranUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface AhlQuranUserRepository extends JpaRepository<AhlQuranUser, Long> {

    Optional<AhlQuranUser> findByUsername(String username);

    @Query("SELECT new com.ahl.alquran.dto.UserResponseDTO(u.name, u.username, u.mobileNumber, u.email) FROM AhlQuranUser u WHERE u.username = :username")
    Optional<UserResponseDTO> findUserByUsername(String username);

    @Modifying
    void deleteByUsername(String username);

    @Modifying
    @Query("UPDATE AhlQuranUser u SET u.authorities = :authorities WHERE u.username = :username")
    void updateUserAuthority(@Param("authorities") Set<String> authorities, @Param("username") String username);

}
