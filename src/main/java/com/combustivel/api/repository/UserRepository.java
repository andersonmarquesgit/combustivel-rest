package com.combustivel.api.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.combustivel.api.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	User findByEmail(String email);
	
	User findOneById(String id);
	
	Set<User> findByIdIn(List<String> ids);
}
