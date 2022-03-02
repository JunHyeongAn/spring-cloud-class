package com.example.restfulwebservice.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restfulwebservice.post.Post;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository postRepository;
	
	@GetMapping("/users")
	public List<User> retirieveAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> findById(@PathVariable("id") int id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent())
			throw new UserNotFoundException(String.format("ID[%s] not fount", id));
		
		EntityModel<User> model = EntityModel.of(user.get());
		
		Link linkToAllUser = linkTo(
			methodOn(this.getClass()).retirieveAllUsers()
		).withRel("all-users");
		
		model.add(linkToAllUser);
		
		return model;
	}
	
	@PostMapping("/users")
	public EntityModel<User> saveUser(@RequestBody User user) {
		User savedUser = userRepository.save(user);
		
		EntityModel<User> model = EntityModel.of(savedUser);
		WebMvcLinkBuilder linkToUserAll = linkTo(
			methodOn(this.getClass()).retirieveAllUsers()
		);
		Link linkToUser = linkTo(
				methodOn(this.getClass()).findById(user.getId())
		).withRel("user");
		
		model.add(linkToUserAll.withRel("all-users"));
		model.add(linkToUser);
		
		return model;
	}
	
	@GetMapping("/users/{id}/posts")
	public List<Post> retrieveAllPostByUser(@PathVariable("id") int id) {
		User user = userRepository.findById(id).get();
		return user.getPosts();
	}
	
	@PostMapping("users/{id}/posts")
	public EntityModel<Post> savePost(@PathVariable("id") int id ,@RequestBody Post post) {
		User user = userRepository.getById(id);
		post.setUser(user);
		Post savedPost = postRepository.save(post);
		
		EntityModel<Post> model = EntityModel.of(savedPost);
		Link linkToPosts = linkTo(methodOn(this.getClass()).retrieveAllPostByUser(id)).withRel("user-posts");
		model.add(linkToPosts);
		
		
		return model;
	}
}






