package com.blog.servicesImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.engine.transaction.jta.platform.internal.SunOneJtaPlatform;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.blog.Dao.CategoryRepo;
import com.blog.Dao.PostRepo;
import com.blog.Dao.UserRepo;
import com.blog.dto.PostDto;
import com.blog.dto.PostResponce;
import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.services.PostService;

import net.bytebuddy.asm.Advice.This;

import org.springframework.data.domain.Sort;
//import net.bytebuddy.asm.Advice.OffsetMapping.Sort;


@Service
public class PostServiceImple implements PostService {

	// we can autowired every service because of in this post we need who update
	// post and from which category
	
	
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		// First we get user and category because for posting we need who posed and in
		// which category posted
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);

	}

//===================================== UpDate Post=================================================
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// first we get  the  all post  after that get post by Id to update particular post
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		// Now we have to set  the entity want to update the  get from the postdto
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		// now we save all in post repository
		Post updatepost = this.postRepo.save(post);
		return this.modelMapper.map(updatepost, PostDto.class);
	}
//======================================= Delete post===============================================

	@Override
	public String deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		postRepo.deleteById(postId);
		return "Post delete Successfully";

	}

//========================================= Get All the Post =============================================

	
	/*
	 * for Gettig all post we need to implementation pagination  
	 * by import "import org.springframework.data.domain.Pageable;"
	 *
	 **/
	@Override
	public PostResponce getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort=sort.by(sortBy).ascending();
			
		}else {
			sort=sort.by(sortBy).descending();
		}
		
		Pageable p= PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Post> pagePost = this.postRepo.findAll(p);
		
		List<Post> allPosts = pagePost.getContent();
		
		List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		PostResponce postResponce= new PostResponce();
		postResponce.setContent(postDtos);
		postResponce.setPageNumber(pagePost.getNumber());
		postResponce.setPageSize(pagePost.getSize());
		postResponce.setTotalElements(pagePost.getTotalElements());
		postResponce.setTotalPages(pagePost.getTotalPages());
		postResponce.setLastpage(pagePost.isLast());
		
		
		return postResponce;
	}

//======================================= Get Post By Id===============================================
	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

		return this.modelMapper.map(post, PostDto.class);
	}
//====================================== Get Post By Category================================================

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {

		// first we have to get categories from database
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		// then we have to find category
		List<Post> posts = this.postRepo.findByCategory(cat);

		// in this we can converted post to postDto and render by map
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

//==================================== Get Post by User ==================================================
	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User users = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userid", userId));

		List<Post> posts = this.postRepo.findByUser(users);

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}
//====================================== Search Post ================================================

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

}
