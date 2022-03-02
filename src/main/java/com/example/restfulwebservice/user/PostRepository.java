package com.example.restfulwebservice.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restfulwebservice.post.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

}
