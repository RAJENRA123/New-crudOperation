package com.bikkadIT.blog_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikkadIT.blog_app.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
