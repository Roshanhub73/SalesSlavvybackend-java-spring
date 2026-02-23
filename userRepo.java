package com.kodnest.project.app.USER;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface userRepo extends JpaRepository<user,Integer> {
Optional<user>findByusername(String username);
Optional<user>findByEmail(String email);
}
