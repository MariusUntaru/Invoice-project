package com.invoice.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorType {

	
	private String time;
	private String status;
	private String message;
	
	public ErrorType(String time, String status, String message) {
		this.time = time;
		this.status = status;
		this.message = message;
	}
}