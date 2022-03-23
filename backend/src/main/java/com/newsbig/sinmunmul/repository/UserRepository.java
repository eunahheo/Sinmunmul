package com.newsbig.sinmunmul.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newsbig.sinmunmul.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findAll();
	
	public Optional<User> findBydelYnAndUserEmailAndUserSgtype(String delYn, String userEmail, String userSgtype);
	
	public Optional<User> findBydelYnAndUserEmail(String delYn, String userEmail);
	
	public Optional<User> findBydelYnAndUserSeq(String delYn, int userSeq);
}
