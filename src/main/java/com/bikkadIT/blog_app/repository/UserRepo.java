package com.bikkadIT.blog_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikkadIT.blog_app.entity.User;

public interface UserRepo extends JpaRepository<User,Long>{

	Optional<User> findByEmail(String email);
}
