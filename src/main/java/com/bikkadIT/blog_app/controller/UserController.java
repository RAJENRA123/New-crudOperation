package com.bikkadIT.blog_app.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkadIT.blog_app.constant.AppConstants;
import com.bikkadIT.blog_app.payloads.UserDto;
import com.bikkadIT.blog_app.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	Logger logger=LoggerFactory.getLogger(CategoryController.class);
   
	@Autowired
	private UserService userService;
     
	/**
	 * @apiNote This Api  is used for create user details
	 * @param userDto
	 * @return
	 */
	
	@PostMapping("/user")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		logger.info("Initiated requst for creat user");
        UserDto saveUser = userService.createUser(userDto);
		logger.info("Completed requst for creat user");
		return new ResponseEntity<UserDto>( saveUser,HttpStatus.CREATED);
		
	}
	
	/**
	 * @apiNote This Api used for update user details
	 * @param userDto
	 * @param userId
	 * @return
	 */
	
	@PutMapping("/user/{id}")
	public ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("id")Long userId){
		logger.info("Initiated request for Update the user details with userId:{}", userId);
        UserDto updateUser = userService.updateUser(userDto, userId);
		logger.info("Completed request for Update the user details with userId:{}", userId);
        return new ResponseEntity<UserDto>(updateUser,HttpStatus.OK);
		
	}
	
	/**
	 * @apiNote This Api is used for Get user details by Id
	 * @param userId
	 * @return
	 */
	@GetMapping("user/{id}")
	public ResponseEntity<UserDto>getSingleUser(@PathVariable("id") Long userId){
		logger.info("Initiated request for get the user details with user:{}", userId);
        UserDto userById = userService.getUserById(userId);
 		logger.info("Completed request for get the user details with userId:{}", userId);
        return new ResponseEntity<UserDto>(userById,HttpStatus.OK);
		
	} 
	
	/**
	 * @apiNote This Api is used for get all user details
	 * @return
	 */
	
	
	@GetMapping("/user")
	public ResponseEntity<List<UserDto>>getAllUsers(){
		logger.info("Initiate requst for get all user details");
        List<UserDto> allUsers = userService.getAllUsers();
		logger.info("Completed requst for get all user details");
		return new ResponseEntity<>(allUsers,HttpStatus.OK);
	}
	
	/**
	 * @apiNote This api is Used for delete user by Id
	 * @param userId
	 * @return
	 */
	@DeleteMapping("/user/{id}")
	public ResponseEntity<String>delteUser(@PathVariable("id") Long userId){
		logger.info("Initiated request for delete the user details with userId:{}", userId);
        userService.deleteUser(userId);
 		logger.info("Completed request for delete the user details with userId:{}", userId);
        return new ResponseEntity<String>(AppConstants.USER_DELETED,HttpStatus.OK);
		
	}
	
}
