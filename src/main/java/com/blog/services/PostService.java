package com.blog.services;

import java.util.List;

import com.blog.dto.PostDto;
import com.blog.dto.PostResponce;
import com.blog.entity.Post;

public interface PostService {

	// Create

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	// Update

	PostDto updatePost(PostDto postDto, Integer postId);

	// Delete

	String deletePost(Integer postId);

	// Get all Post

	PostResponce getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

	// get Single Post

	PostDto getPostById(Integer postId);
	
	// Get all post by category
	
	List<PostDto> getPostByCategory(Integer categoryId);
	
	// Get all post by User
	
	List<PostDto> getPostByUser(Integer userId);
	
	// Search Post
	
	List<PostDto> searchPosts(String keyword);

}
