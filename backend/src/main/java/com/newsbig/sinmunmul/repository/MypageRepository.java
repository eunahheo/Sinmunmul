package com.newsbig.sinmunmul.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.entity.User;

@Repository
public interface MypageRepository extends JpaRepository<User, Integer> {
	public Optional<User> findByUserSeq(int userSeq);
}
