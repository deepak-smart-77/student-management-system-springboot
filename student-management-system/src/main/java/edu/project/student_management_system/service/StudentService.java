package edu.project.student_management_system.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.project.student_management_system.dto.PageResponseDTO;
import edu.project.student_management_system.dto.StudentRequestDTO;
import edu.project.student_management_system.dto.StudentResponseDTO;
import edu.project.student_management_system.entity.Student;
import edu.project.student_management_system.exception.StudentNotFoundException;
import edu.project.student_management_system.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentrepository;

	private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

	public StudentResponseDTO addStudent(StudentRequestDTO requestDTO) {

		logger.info("Adding new student");
		// DTO -> Entity
		Student student = new Student();
		student.setName(requestDTO.getName());
		student.setDepartment(requestDTO.getDepartment());
		student.setEmail(requestDTO.getEmail());

		// Save to DB
		Student savedStudent = studentrepository.save(student);

		// Entity to ResponseDTO

//		StudentResponseDTO responseDTO = new StudentResponseDTO();
//		responseDTO.setId(savedStudent.getId());
//		responseDTO.setName(savedStudent.getName());
//		responseDTO.setDepartment(savedStudent.getDepartment());
//		responseDTO.setEmail(savedStudent.getEmail());
		
		logger.info("Student added successfully with id: {}", savedStudent.getId());

		return mapToResponseDTO(savedStudent);
	}

	public List<StudentResponseDTO> getAllStudents() {

		List<Student> students = studentrepository.findAll();

		List<StudentResponseDTO> responseList = new ArrayList<>();

		for (Student student : students) {

//			StudentResponseDTO responseDTO = new StudentResponseDTO();
//			responseDTO.setId(student.getId());
//			responseDTO.setName(student.getName());
//			responseDTO.setDepartment(student.getDepartment());
//			responseDTO.setEmail(student.getEmail());

			responseList.add(mapToResponseDTO(student));
		}

		return responseList;
	}

	public StudentResponseDTO getstudentById(Long id) {
		Student student = studentrepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

		return mapToResponseDTO(student);

	}

	public String deleteStudent(Long id) {
		
		logger.warn("Deleting student with id: {}", id);
		
		Student student = studentrepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
		studentrepository.delete(student);

		return "Student Deleted Successfully";
	}

	public StudentResponseDTO updateStudent(Long id, StudentRequestDTO requestDTO) {
		
		logger.info("Updating student with id: {}", id);
		
		Student existingStudent = studentrepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

		existingStudent.setName(requestDTO.getName());
		existingStudent.setDepartment(requestDTO.getDepartment());
		existingStudent.setEmail(requestDTO.getEmail());

		Student updatedStudent = studentrepository.save(existingStudent);

		return mapToResponseDTO(updatedStudent);
	}

	public List<StudentResponseDTO> getStudentByDepartment(String department) {
		List<Student> students = studentrepository.findByDepartment(department);
		List<StudentResponseDTO> responseList = new ArrayList<>();
		for (Student student : students) {
			responseList.add(mapToResponseDTO(student));
		}
		return responseList;

	}

	public List<StudentResponseDTO> getStudentByName(String name) {
		List<Student> students = studentrepository.findByNameContaining(name);
		List<StudentResponseDTO> responseList = new ArrayList<>();

		for (Student student : students) {
			responseList.add(mapToResponseDTO(student));
		}
		return responseList;
	}

	public PageResponseDTO<StudentResponseDTO> getStudentsWithPaginationAndSorting(int page, int size, String sortBy,
			String direction) {

		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Student> studentPage = studentrepository.findAll(pageable);

		List<StudentResponseDTO> responseList = new ArrayList<>();

		for (Student student : studentPage.getContent()) {
			responseList.add(mapToResponseDTO(student));
		}

		PageResponseDTO<StudentResponseDTO> pageResponse = new PageResponseDTO<>();

		pageResponse.setContent(responseList);

		pageResponse.setPageNumber(studentPage.getNumber());

		pageResponse.setPageSize(studentPage.getSize());

		pageResponse.setTotalElements(studentPage.getTotalElements());

		pageResponse.setTotalPages(studentPage.getTotalPages());

		pageResponse.setLastPage(studentPage.isLast());

		return pageResponse;
	}

	private StudentResponseDTO mapToResponseDTO(Student student) {

		StudentResponseDTO responseDTO = new StudentResponseDTO();
		responseDTO.setId(student.getId());
		responseDTO.setName(student.getName());
		responseDTO.setDepartment(student.getDepartment());
		responseDTO.setEmail(student.getEmail());

		return responseDTO;
	}
}
