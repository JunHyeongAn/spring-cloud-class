package com.example.restfulwebservice.post;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.restfulwebservice.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	@Id
	@GeneratedValue
	private Integer id;
	
	private String description;
	
	// User : Post -> 1 : M
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;
}
