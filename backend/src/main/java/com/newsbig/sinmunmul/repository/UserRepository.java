package com.newsbig.sinmunmul.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newsbig.sinmunmul.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findAll();
}
