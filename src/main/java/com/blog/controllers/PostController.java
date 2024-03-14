package com.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstants;
import com.blog.dto.ApiResponce;
import com.blog.dto.PostDto;
import com.blog.dto.PostResponce;
import com.blog.services.FileService;
import com.blog.services.PostService;


@RestController
@RequestMapping
public class PostController {
	
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	//======================================= Create Post==============================================
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> cratePost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}
	
	//===================================== Get Post By Category ======================================
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
		List<PostDto> posts = this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//======================================= Get Post By User =======x=================================
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
		List<PostDto> posts = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}	
	
	//======================================= Get All Post=========================================
	
	@GetMapping("/allposts")
	/*
	 * for taking the  value from the user we pass @RequestParam
	 */
	public ResponseEntity<PostResponce> getAllPost(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER ,required = false) Integer pageNumber,
													@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
													@RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
													@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
													){
		 PostResponce postResponce = this.postService.getAllPost(pageNumber, pageSize,sortBy,sortDir);
		return  new ResponseEntity<PostResponce>(postResponce,HttpStatus.OK);
	}
	//======================================= Get Post By Id ==========================================
	@GetMapping("/GetPost/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		return ResponseEntity.ok(this.postService.getPostById(postId));
		}
	
	//======================================= Delete Post By Id ========================================
	
	@DeleteMapping("/deletePost/{postId}")
	public ResponseEntity<ApiResponce> deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponce>(new ApiResponce("Post Deleted Successfully !!", true), HttpStatus.OK);
	}
	
	//======================================= Update Post ===========================================
	
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> UpdatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	//=======================================Search=================================================
	
	@GetMapping("/post/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitile(@PathVariable("keywords") String keywords){
		List<PostDto> result = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
	//=======================================Post Image Upload===========================================
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException{
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	
	//========================================method to serve files===================================
	
	@GetMapping(value ="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE )
	public void downloadImage( @PathVariable("imageName") String imageName,HttpServletResponse response ) throws IOException {
		 InputStream resource= this.fileService.getResource(path, imageName);
		 response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		 StreamUtils.copy(resource, response.getOutputStream());
		
	}
}






















