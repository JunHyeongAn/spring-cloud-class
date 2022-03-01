package com.example.restfulwebservice.user;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {
	@Autowired
	private UserDaoService service;
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable("id") int id) {
		User user = service.findOne(id);
		if(user == null) userNotFountExcption(id);
		
		// HATEOAS
		EntityModel<User> model = EntityModel.of(user);
		WebMvcLinkBuilder linkTo = linkTo(
				methodOn(this.getClass()).retrieveAllUsers());
		
		Link linkToUpdate = linkTo(
					methodOn(this.getClass()).modifyUser(user)
				).withRel("update-user");
		
		Link self = linkTo(methodOn(this.getClass()).retrieveUser(id)).withSelfRel();
		
		model.add(linkTo.withRel("all-users"));
		model.add(linkToUpdate);
		model.add(self);
		return model;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId())
			.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable("id")int id) {
		User user = service.deleteById(id);
		
		if(user == null) userNotFountExcption(id);
	}
	
	@PutMapping("/users")
	public User modifyUser(@RequestBody User user) {
		User _user = service.findOne(user.getId());
		if(_user == null) userNotFountExcption(user.getId());
		user.setJoinDate(_user.getJoinDate());
		service.modifyUser(user);
		return _user;
	}
	
	public void userNotFountExcption(int id) {
		throw new UserNotFoundException(String.format("ID[%s] not found", id));
	}
}
