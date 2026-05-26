package edu.project.student_management_system.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.project.student_management_system.dto.LoginRequestDTO;
import edu.project.student_management_system.dto.SignupRequestDTO;
import edu.project.student_management_system.entity.User;
import edu.project.student_management_system.repository.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userrepository;
	
	@Autowired
	private PasswordEncoder passwordencoder;
	
	public String signup(SignupRequestDTO requestDTO) {
		User user = new User();
		
		user.setUsername(requestDTO.getUsername());
		user.setEmail(requestDTO.getEmail());
		user.setPassword(passwordencoder.encode(requestDTO.getPassword()));
		
		userrepository.save(user);
		
		return "User registered successfully";
	}
	public String login(LoginRequestDTO requestDTO) {
		Optional<User> optionalUser = userrepository.findByEmail(requestDTO.getEmail());
		if(optionalUser.isEmpty()) {
			return "Invalid email or password";
		}
		User user = optionalUser.get();
		boolean passwordMatches = passwordencoder.matches(requestDTO.getPassword(), user.getPassword());
		if(!passwordMatches) {
			return "Invalid email or password";
		}
		return "Login successful";
	}
}
