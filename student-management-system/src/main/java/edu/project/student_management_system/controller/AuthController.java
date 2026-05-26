package edu.project.student_management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.project.student_management_system.dto.LoginRequestDTO;
import edu.project.student_management_system.dto.SignupRequestDTO;
import edu.project.student_management_system.response.ApiResponse;
import edu.project.student_management_system.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService authservice;
	
	@PostMapping("/signup")
	public ApiResponse<String> signup(@Valid @RequestBody SignupRequestDTO requestDTO) {
		String message = authservice.signup(requestDTO);
		return new ApiResponse<String>(true, message, null);
	}
	@PostMapping("/login")
	public ApiResponse<String> login(@Valid @RequestBody LoginRequestDTO requestDTO){
		String message = authservice.login(requestDTO);
		return new ApiResponse<>(true,message,null); 
	}
}
