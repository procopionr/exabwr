package com.procopior.exabwr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.procopior.exabwr.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<User> findByEmail(final String email);

	Optional<User> findByUsername(final String username);
	
}
