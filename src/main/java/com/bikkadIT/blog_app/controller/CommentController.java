package com.bikkadIT.blog_app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkadIT.blog_app.constant.AppConstants;
import com.bikkadIT.blog_app.payloads.ApiResponse;
import com.bikkadIT.blog_app.payloads.CommentDto;
import com.bikkadIT.blog_app.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
    
	Logger logger=LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	private CommentService commentService;
	
	/**
	
	 * @apiNote This Api is used for create comments
	 * @param comment
	 * @param postId
	 * @return
	 */
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment (@RequestBody CommentDto comment, @PathVariable Integer postId){
    logger.info("Initiated requst for creat Comments");
    CommentDto createComment = this.commentService.createComment(comment , postId ); 	
    logger.info("Completed requst for creat Comments");
    return new ResponseEntity<CommentDto>(createComment , HttpStatus.CREATED);
	}
	
	/**
	 
	 * @apiNote This Api is used for delete comments
	 * @param commentId
	 * @return
	 */
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<String> deleteComment (@PathVariable Integer commentId){
	logger.info("Initiated request for delete the Comments with commentId:{}", commentId);
    this.commentService.deleteComment(commentId) ;  
	logger.info("Completed request for delete the comments with commentId:{}", commentId);
     return new ResponseEntity<String>(AppConstants.COMMENT_DELETED , HttpStatus.OK);
	}
}
