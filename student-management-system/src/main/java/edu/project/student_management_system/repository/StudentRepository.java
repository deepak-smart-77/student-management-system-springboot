package edu.project.student_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.project.student_management_system.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	List<Student> findByDepartment(String department);
	List<Student> findByNameContaining(String name);
}
