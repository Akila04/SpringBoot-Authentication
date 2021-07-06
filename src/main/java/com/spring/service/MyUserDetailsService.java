package com.spring.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.UserCredentials;
import com.spring.UserCredentialsRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserCredentialsRepository userCredentialsRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserCredentials userCredential=null;
		List<UserCredentials> userlist =userCredentialsRepository.findAll();
		for(UserCredentials user : userlist) {
			if(user.getUserid().equals(username)){
				userCredential=user;
				break;
			}
		}
		
		return new User(userCredential.getUserid(), userCredential.getPassword(),new ArrayList<>());
		
		//return new User("foo", "foo", Arrays.asList());
	}

}

