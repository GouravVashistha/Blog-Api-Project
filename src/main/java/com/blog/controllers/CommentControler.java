package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blog.dto.ApiResponce;
import com.blog.dto.CommentDto;
import com.blog.services.CommentService;


@RestController("./api/")
public class CommentControler {
	@Autowired
	private CommentService commentService;

	
	
	@PostMapping("/post/{post-id}/comments/{user-id}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment,
													@PathVariable("post-id") Integer postId,
													@PathVariable("user-id") Integer userId){
		CommentDto createComment = this.commentService.createComment(comment, postId, userId);
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponce> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponce>(new ApiResponce("Deleted Comment Successfully !!",true),HttpStatus.OK);
	}
	
	
	
	
	
	
}
