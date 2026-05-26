package edu.project.student_management_system.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

	private boolean success;

	private String message;

	private T data;

//we use all argument constructor annotation
//	public ApiResponse(boolean success, String message, T data) {
//
//		this.success = success;
//		this.message = message;
//		this.data = data;
//	}

}
