package com.bikkadIT.blog_app.service;

import java.util.List;

import com.bikkadIT.blog_app.payloads.UserDto;



public interface UserService {

	UserDto createUser(UserDto userDto);
	
	UserDto updateUser(UserDto userDto,Long userId);
	
	UserDto getUserById(Long userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Long userId);
	
	
}
