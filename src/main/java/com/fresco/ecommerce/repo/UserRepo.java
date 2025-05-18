package com.fresco.ecommerce.repo;

import java.util.Optional;

import com.fresco.ecommerce.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
	Optional<UserEntity> findByUsername(String username);
}