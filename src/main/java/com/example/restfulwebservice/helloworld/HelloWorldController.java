package com.example.restfulwebservice.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	// GET
	// /hello-world > endpoint
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path = "hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping(path = "hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World Bean");
	}
	
	@GetMapping(path = "hello-world-bean/{name}")
	public HelloWorldBean helloWorldBean(@PathVariable("name") String name) {
		return new HelloWorldBean(String.format("Hello World Bean %s", name));
	}
	
	@GetMapping(path = "/hello-world-internationalized")
	public String helloWordlInternationalized(@RequestHeader(name="Accept-Language", required = false) Locale locale) {
		return messageSource.getMessage("greeting.message", null, locale);
	}
}
