package com.bikkadIT.blog_app.payloads;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bikkadIT.blog_app.entity.Category;
import com.bikkadIT.blog_app.entity.Comment;
import com.bikkadIT.blog_app.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	
	private Integer postId;
	@NotNull 
	@Size(min = 3,max = 10,message = "Min 3 chars and Max 10 Chars..!!")
	private String  title;
	
	@NotNull 
	@Size(min = 10,max = 1000,message = "Min 10 chars and Max 1000 Chars..!!")
	private  String content;
	
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private Set<CommentDto> comments= new HashSet<>();

}
