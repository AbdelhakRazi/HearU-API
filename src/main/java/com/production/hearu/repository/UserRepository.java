package com.production.hearu.repository;

import com.production.hearu.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "users")
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email); // useful for defining when retrieving user first

}
