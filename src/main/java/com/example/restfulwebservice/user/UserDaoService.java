package com.example.restfulwebservice.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restfulwebservice.post.Post;

@Service
public class UserDaoService {
	private static List<User> users = new ArrayList<User>();
	static {
		users.add(new User(1, "kenneth", new Date(), "pass1", "ssn1", new ArrayList<Post>()));
		users.add(new User(2, "kenneth1", new Date(), "pass2", "ssn2", new ArrayList<Post>()));
		users.add(new User(3, "kenneth2", new Date(), "pass3", "ssn3", new ArrayList<Post>()));
	}
	
	private static int usersCount = users.size();
	
	public List<User> findAll() {
		return users;
	}
	
	public User save(User user) {
		if(user.getId() == null) {
			user.setId(++usersCount);
		}
		
		users.add(user);
		return user;
	}
	
	public User findOne(int id) {
		for(User user : users) {
			if(user.getId() == id)
				return user;
		}
		return null;
	}
	
	public User deleteById(int id) {
		User user = findOne(id);
		if(user != null)
			users.remove(user);
		return user;
	}
	
	public User modifyUser(User user) {
		deleteById(user.getId());
		save(user);
		return user;
	}
}
