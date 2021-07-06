package com.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.service.MyUserDetailsService;
import com.spring.util.JwtUtil;

@SpringBootApplication
@RestController
public class DemoSpringAuth1Application {
	
	@Autowired
	private UserCredentialsRepository userCredentialsRepository;
	
	@Autowired
	private AuthenticationManager authenticationmanager;
	
	@Autowired
	private MyUserDetailsService myuserdetailsservice;
	
	@Autowired
	private JwtUtil jwtutil;
	
	//Welcome Api
	@RequestMapping("/welcome")
	public String showwelcome() {
		return "Welcome";
	}
	
	//Authenticate Api
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public String createJwtToken(@RequestBody UserCredentials userCredentials) throws Exception {
		
		int flag = 0;
		List<UserCredentials> UsersList = userCredentialsRepository.findAll();
		
		
		for(UserCredentials user: UsersList) {
			if(user.getUserid().equals(userCredentials.getUserid()) && user.getPassword().equals(userCredentials.getPassword())) {
				flag = 1;
				break;
			}
		}
		
		if(flag == 0) {
			return "Incorrect User Name or Password";
		}
		
		
		try {

			authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getUserid(), userCredentials.getPassword()));
		
		} catch(BadCredentialsException e) {
			
			throw new Exception("Incorrect Username and Password");
		}
		
		final UserDetails userDetails = myuserdetailsservice.loadUserByUsername(userCredentials.getUserid());
		
		String jwtToken  = jwtutil.generateToken(userDetails);
		
		System.out.println("TOEKN: " + jwtToken);
		
		return jwtToken;
		
	}
	
	public boolean isuserIdexist(String userId) {
			
			List<UserCredentials> userlist =userCredentialsRepository.findAll();
			for(UserCredentials user : userlist) {
				if(user.getUserid().equals(userId)){
					return true;
				}
			}
			return false;
		}
	
	//Add New User Api
	@RequestMapping(path="/addnewuser" ,method=RequestMethod.POST)
	public String setnewuser(@RequestBody UserCredentials  userCredentials) throws Exception {
		
		
		if(!isuserIdexist(userCredentials.getUserid())) {
			userCredentialsRepository.save(userCredentials);
			
			try {
				authenticationmanager.authenticate(
						new UsernamePasswordAuthenticationToken(userCredentials.getUserid(), userCredentials.getPassword())
				);
			}
			catch (BadCredentialsException e) {
				throw new Exception("Incorrect username or password", e);
			}


			final UserDetails userDetails = myuserdetailsservice.loadUserByUsername(userCredentials.getUserid());

			final String jwt = jwtutil.generateToken(userDetails);

			
			return jwt;
		}
		else {
			return "User Id alreadt Exist";
		}
		
	}


	public static void main(String[] args) {
		SpringApplication.run(DemoSpringAuth1Application.class, args);
	}

}
