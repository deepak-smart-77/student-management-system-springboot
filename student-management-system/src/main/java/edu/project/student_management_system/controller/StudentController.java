package edu.project.student_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.project.student_management_system.dto.PageResponseDTO;
import edu.project.student_management_system.dto.StudentRequestDTO;
import edu.project.student_management_system.dto.StudentResponseDTO;
import edu.project.student_management_system.response.ApiResponse;
import edu.project.student_management_system.service.StudentService;
import jakarta.validation.Valid;

@RestController
public class StudentController {

	@Autowired
	StudentService studentservice;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/students")
	public ApiResponse<StudentResponseDTO> addStudent(@Valid @RequestBody StudentRequestDTO requestDTO) {

		StudentResponseDTO responseDTO = studentservice.addStudent(requestDTO);

		return new ApiResponse<>(true, "Student Added Successfully", responseDTO);
	}
	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@GetMapping("/students")
	public ApiResponse<List<StudentResponseDTO>> getAllStudent() {

		List<StudentResponseDTO> responseDTO = studentservice.getAllStudents();

		return new ApiResponse<>(true, "Students fetched Successfully", responseDTO);
	}
	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@GetMapping("/students/{id}")
	public ApiResponse<StudentResponseDTO> getStudentById(@PathVariable Long id) {

		StudentResponseDTO responseDTO = studentservice.getstudentById(id);

		return new ApiResponse<>(true, "Student fetched successfully", responseDTO);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/students/{id}")
	public ApiResponse<String> deleteStudent(@PathVariable Long id) {

		String message = studentservice.deleteStudent(id);

		return new ApiResponse<>(true, message, null);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/students/{id}")
	public ApiResponse<StudentResponseDTO> updateStudent(@PathVariable Long id,
			@Valid @RequestBody StudentRequestDTO requestDTO) {

		StudentResponseDTO responseDTO = studentservice.updateStudent(id, requestDTO);

		return new ApiResponse<>(true, "Student Updated Successfully", responseDTO);
	}

	@GetMapping("/students/department/{department}")
	public ApiResponse<List<StudentResponseDTO>> getStudentByDepartment(@PathVariable String department) {

		List<StudentResponseDTO> students = studentservice.getStudentByDepartment(department);

		return new ApiResponse<>(true, "Student fetched Successfully", students);
	}

	@GetMapping("/students/name/{name}")
	public ApiResponse<List<StudentResponseDTO>> getStudentByName(@PathVariable String name) {

		List<StudentResponseDTO> students = studentservice.getStudentByName(name);

		return new ApiResponse<>(true, "Students fetched Successfully", students);
	}
	
	@GetMapping("/students/paginated")
	public ApiResponse<PageResponseDTO<StudentResponseDTO>> getStudentWithPagination(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam String direction){
		
		PageResponseDTO<StudentResponseDTO> response = studentservice.getStudentsWithPaginationAndSorting(page, size, sortBy, direction);
		
		return new ApiResponse<>(true,"Students fetched Successfully",response);
	}
}
