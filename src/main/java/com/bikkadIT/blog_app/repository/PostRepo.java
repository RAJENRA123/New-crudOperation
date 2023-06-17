package com.bikkadIT.blog_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikkadIT.blog_app.entity.Category;
import com.bikkadIT.blog_app.entity.Post;
import com.bikkadIT.blog_app.entity.User;

public interface PostRepo  extends JpaRepository<Post,Integer>{
	
	List<Post>findAllByUser(User user);
	
	List<Post>findByCategory(Category category);
	
    List<Post>findByTitleContaining(String title);

}
