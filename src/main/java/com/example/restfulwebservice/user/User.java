package com.example.restfulwebservice.user;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.example.restfulwebservice.post.Post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value = {"password"})
//@JsonFilter(value = "UserInfo")
@Entity
public class User {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min = 2, message = "Name은 2글자 이상 입력해주세요")
	private String name;
	@Past
	private Date joinDate;
	
	private String password;
	private String ssn;
	
	@OneToMany(mappedBy = "user")
	private List<Post> posts;
}
