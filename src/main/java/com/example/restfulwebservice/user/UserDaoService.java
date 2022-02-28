package com.example.restfulwebservice.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserDaoService {
	private static List<User> users = new ArrayList<User>();
	static {
		users.add(new User(1, "kenneth", new Date()));
		users.add(new User(2, "kenneth1", new Date()));
		users.add(new User(3, "kenneth2", new Date()));
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
}
