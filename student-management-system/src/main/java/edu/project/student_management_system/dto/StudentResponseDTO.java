package edu.project.student_management_system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentResponseDTO {
	
	private Long id;
    private String name;
    private String department;
    private String email;

    
}
