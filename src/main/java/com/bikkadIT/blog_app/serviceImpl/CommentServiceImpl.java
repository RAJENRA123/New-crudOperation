package com.bikkadIT.blog_app.serviceImpl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikkadIT.blog_app.entity.Comment;
import com.bikkadIT.blog_app.entity.Post;
import com.bikkadIT.blog_app.exception.ResourceNotFoundException;
import com.bikkadIT.blog_app.payloads.CommentDto;
import com.bikkadIT.blog_app.repository.CommentRepo;
import com.bikkadIT.blog_app.repository.PostRepo;
import com.bikkadIT.blog_app.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
    
	Logger logger=LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired 
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
	    logger.info("Initiating dao call for creat Comments");
        Post post=this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        Comment comment=this.modelMapper.map(commentDto , Comment.class);
	    comment.setPost(post);
	    Comment savedComment=this.commentRepo.save(comment);
	    logger.info("Complete dao call for creat Comments");

		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		logger.info("Initiating dao call for delete the Comment with commentId:{}", commentId);
        Comment com=this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));
 		logger.info("Completed dao for delete the Comment with commentId:{}", commentId);

		this.commentRepo.delete(com);
	}

}
