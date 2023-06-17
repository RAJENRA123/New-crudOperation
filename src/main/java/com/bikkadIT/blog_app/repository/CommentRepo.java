package com.bikkadIT.blog_app.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;

import com.bikkadIT.blog_app.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
