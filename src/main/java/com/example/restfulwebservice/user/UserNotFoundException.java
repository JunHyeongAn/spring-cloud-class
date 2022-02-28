package com.example.restfulwebservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


// status code 500번 발생 근데 코드가 노출될수 있으니까 다른 코드로 해야한다.
// HTTP Status Code
// 2xx => ok
// 4xx => client
// 5xx => server 
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
