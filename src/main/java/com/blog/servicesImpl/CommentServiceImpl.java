package com.blog.servicesImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.Dao.CommentRepo;
import com.blog.Dao.PostRepo;
import com.blog.Dao.UserRepo;
import com.blog.dto.CommentDto;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId,Integer userId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		comment.setUser(user);
		
		
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment com=this.commentRepo.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));
		
		this.commentRepo.delete(com);
	}

}
