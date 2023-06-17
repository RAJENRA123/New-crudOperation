package com.bikkadIT.blog_app.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	
	private Long userId;
	
	@NotNull 
	@Size(min = 4,message = "Username Must be Min 4 Chars...!!")
	private String userName;
	
	@Email
	private String email;
	
	@NotEmpty
	@Size(min = 3,max = 10,message = "Min 3 chars and Max 10 Chars..!!")
	private String password;
	
	private String about;
}
