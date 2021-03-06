package com.example.restfulwebservice.user;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserDaoService service;
	
	@GetMapping("/users")
	public MappingJacksonValue retrieveAllUsers() {
		List<User> users = service.findAll();
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
				.filterOutAllExcept("id", "name");
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
	
//	@GetMapping("/v1/users/{id}")
	@GetMapping(value = "/users/{id}", params = "version=1")
	public MappingJacksonValue retrieveUserV1(@PathVariable("id") int id) {
		User user = service.findOne(id);
		if(user == null) userNotFountExcption(id);
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
				.filterOutAllExcept("id", "name", "joinDate", "ssn");
				
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(user);
		mapping.setFilters(filters);
		
		return mapping;
	}
	
//	@GetMapping("/v2/users/{id}")
	@GetMapping(value = "/users/{id}", params = "version=2")
	public MappingJacksonValue retrieveUserV2(@PathVariable("id") int id) {
		User user = service.findOne(id);
		if(user == null) userNotFountExcption(id);
		
		UserV2 userV2 = new UserV2();
		BeanUtils.copyProperties(user, userV2);
		userV2.setGrade("VIP");
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
				.filterOutAllExcept("id", "name", "joinDate", "grade");
				
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(userV2);
		mapping.setFilters(filters);
		
		return mapping;
	}
	
	public void userNotFountExcption(int id) {
		throw new UserNotFoundException(String.format("ID[%s] not found", id));
	}
}
