package com.bikkadIT.blog_app.controller;


//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bikkadIT.blog_app.constant.AppConstants;
import com.bikkadIT.blog_app.payloads.ApiResponse;
import com.bikkadIT.blog_app.payloads.PostDto;
import com.bikkadIT.blog_app.payloads.PostResponse;
import com.bikkadIT.blog_app.service.FileService;
import com.bikkadIT.blog_app.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	Logger logger=LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	/**
	 * @apiNote This Api is used for create post
	 * @param postDto
	 * @param userId
	 * @param categoryId
	 * @return
	 */

	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Long userId,
			@PathVariable Integer categoryId) {
		logger.info("Initiated requst for creat Posts");
        PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		logger.info("Completed requst for creat Posts");
        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

	}

	
	/**
	 * @apiNote This Api is  Used for update post 
	 * @param postDto
	 * @param postId
	 * @return
	 */
	
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		logger.info("Initiated request for Update the Posts details with postId:{}", postId);
        PostDto updatePost = this.postService.updatePost(postDto, postId);
		logger.info("Completed request for Update the Posts details with postId:{}", postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	/**
	 * @apiNote This Api is used for get all posts
	 * @param pageSize
	 * @param pageNumber
	 * @param sortBy
	 * @param sortDir
	 * @return
	 */
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		logger.info("Initiate requst for get all Posts details");
        PostResponse postResponse = this.postService.getAllPost(pageSize, pageNumber, sortBy, sortDir);
		logger.info("Completed requst for get all Posts details");
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	/**
	 * @apiNote This Api is used for get post by Id
	 * @param postId
	 * @return
	 */
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		logger.info("Initiated request for get the Post details with postId:{}", postId);
        PostDto postById = this.postService.getPostById(postId);
 		logger.info("Completed request for get the Post details with postId:{}", postId);
        return new ResponseEntity<PostDto>(postById, HttpStatus.OK);
	}

	/**
	 * @apiNote This api is used for Delete posts
	 * @param postId
	 * @return
	 */

	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		logger.info("Initiated request for delete the Posts  with postId:{}", postId);
        this.postService.deletePost(postId);
 		logger.info("Completed request for delete the posts with postId:{}", postId);
        return new ApiResponse("post Deleted Successfully", true);
	}
    
	/**
	 * @apiNote This Api is Used for post get by userId
	 * @param userId
	 * @return
	 */
	
	@GetMapping("/user/users/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId) {
		logger.info("Initiated request for get the post by user with userId:{}", userId);
        List<PostDto> posts = this.postService.getPostsByUser(userId);
 		logger.info("Completed request for get the post by user with userId:{}", userId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}

	
     /**
      * @apiNote This Api is used for post get by category
      * @param categoryId
      * @return
      */
	
	@GetMapping("/user/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
		logger.info("Initiated request for get the post by category with categoryId:{}", categoryId);
        List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
 		logger.info("Completed request for get the post by category with categoryId:{}", categoryId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}

	/**
	 * @apiNote This Api is used for search posts
	 * @param keyword
	 * @return
	 */
	
	@GetMapping("posts/search/{keyword}")
	public ResponseEntity<List<PostDto>>serachPostByTitle(@PathVariable("keyword") String keyword){
		logger.info("Initiated requst for search Posts");
        List<PostDto> result = this.postService.searchPosts(keyword);
		logger.info("Completed requst for search Posts");
        return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
		
	}
	
	/**
	 * @apiNote This api is used for upload image
	 * @param image
	 * @param postId
	 * @return
	 * @throws IOException
	 */
	
	@PostMapping("/post/image/uplod/{postId}")
	public ResponseEntity<PostDto>uplodPostImage(
			@RequestParam ("image")MultipartFile image,@PathVariable Integer postId) throws IOException{
		logger.info("Initiated request for upload the image  with postId:{}", postId);
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uplodImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
 		logger.info("Completed request for upload the image  with postId:{}", postId);
        return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	/**
	 * @apiNote This api is used for image by imageName
	 * @param imageName
	 * @param response
	 * @throws IOException
	 */
	
	
	@GetMapping(value="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName")String imageName,HttpServletResponse response) throws IOException {
		logger.info("Initiated request for get the image  with imageName:{}", imageName);
        InputStream resource = this.fileService.getResource(path, imageName);
 		logger.info("Completed request for get the image  with imageName:{}", imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
}
