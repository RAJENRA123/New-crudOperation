package com.bikkadIT.blog_app.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bikkadIT.blog_app.entity.User;
import com.bikkadIT.blog_app.exception.ResourceNotFoundException;
import com.bikkadIT.blog_app.repository.UserRepo;

@Component
public class CustomUserDetailService implements UserDetailsService {
	
    @Autowired
	private UserRepo userRepo;
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		 User user = this.userRepo.findByEmail(username)
				 .orElseThrow(()-> new ResourceNotFoundException("User","email: "+username,0));
		
		return user;
	}

}
