package com.bikkadIT.blog_app.serviceImpl;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikkadIT.blog_app.constant.AppConstants;
import com.bikkadIT.blog_app.entity.User;
import com.bikkadIT.blog_app.exception.*;
import com.bikkadIT.blog_app.payloads.UserDto;
import com.bikkadIT.blog_app.repository.UserRepo;
import com.bikkadIT.blog_app.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
    
	@Autowired
	private UserRepo  userRepo;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Override
   public UserDto createUser(UserDto userDto) {
		logger.info("Initiating dao call for creat user");
        //convert userDto to Entity
		//User user = this.dtoToUser(userDto);
	    // User savedUser= this.userRepo.save(user);
	    //	return this.userToDto(savedUser);
		
		//ModelMapper-One object convert into another Object
		User user = modelMapper.map(userDto, User.class);
		User savedUser = userRepo.save(user);
		UserDto dto = modelMapper.map(savedUser, UserDto.class);
		logger.info("Completed dao call for creat user");
        return dto;
	
	}

	@Override
	public UserDto updateUser(UserDto userDto, Long userId) {
	logger.info("Initiating dao call for Update the user details with userId:{}", userId);
    User user2 = userRepo.findById(userId).orElseThrow(() ->new ResourceNotFoundException(AppConstants.USER_NOT_FOUND+userId, "User", userId));
	user2.setUserId(userDto.getUserId());
	user2.setUserName(userDto.getUserName());
	user2.setEmail(userDto.getEmail());
	user2.setPassword(userDto.getPassword());
	user2.setAbout(userDto.getAbout());
    User updatedUser = userRepo.save(user2);
	logger.info("Completed dao call for Update the user details with userId:{}", userId);
    
	return modelMapper.map(updatedUser,UserDto.class);
	}

	@Override
	public UserDto getUserById(Long userId) {
		logger.info("Initiating dao call for get the user details with user:{}", userId);
        User user = userRepo.findById(userId).orElseThrow(() ->new ResourceNotFoundException(AppConstants.USER_NOT_FOUND+userId, "User", userId));
 		logger.info("Completed dao call for get the user details with userId:{}", userId);
        return modelMapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {
		logger.info("Initiate dao call for get all user details");
        List<User> allUsers = userRepo.findAll();
		List<UserDto> list = allUsers.stream().map((users)->modelMapper.map(users, UserDto.class))
				.collect(Collectors.toList());
			logger.info("Completed dao call for get all user details");
         return list;
	}

	@Override
	public void deleteUser(Long userId) {
		logger.info("Initiating dao call for delete the user details with userId:{}", userId);
        User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException(AppConstants.USER_DELETED+userId, "User", userId));
 		logger.info("Completed dao call for delete the user details with userId:{}", userId);
        userRepo.delete(user);

	}
}