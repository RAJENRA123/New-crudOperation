package com.bikkadIT.blog_app.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bikkadIT.blog_app.constant.AppConstants;
import com.bikkadIT.blog_app.entity.Category;
import com.bikkadIT.blog_app.entity.Post;
import com.bikkadIT.blog_app.entity.User;
import com.bikkadIT.blog_app.exception.ResourceNotFoundException;
import com.bikkadIT.blog_app.payloads.PostDto;
import com.bikkadIT.blog_app.payloads.PostResponse;
import com.bikkadIT.blog_app.repository.CategoryRepo;
import com.bikkadIT.blog_app.repository.PostRepo;
import com.bikkadIT.blog_app.repository.UserRepo;
import com.bikkadIT.blog_app.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	Logger logger=LoggerFactory.getLogger(PostServiceImpl.class);
	
	@Autowired
	private PostRepo postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private UserRepo userRepository;

	@Override
	public PostDto createPost(PostDto postDto, Long userId, Integer categoryId) {
		logger.info("Initiating dao call  for creat Posts");

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userId, "User", userId));

		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId,
						"Category", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
        Post newPost = this.postRepository.save(post);
		logger.info("Completed dao call for creat Posts");

		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		logger.info("Initiating dao call for Update the Posts details with postId:{}", postId);
        Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
        Post updatedpost = this.postRepository.save(post);
		logger.info("Completed dao call for Update the Posts details with postId:{}", postId);

		return this.modelMapper.map(updatedpost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		logger.info("Initiating dao call for delete the Posts details with postId:{}", postId);
        Post post = this.postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND + postId, "post id", postId));
 		logger.info("Completed dao call for delete the posts details with postId:{}", postId);

		this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageSize, Integer pageNumber, String sortBy, String sortDir) {
		logger.info("Initing dao call for get All Post");
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());

		Page<Post> pagePost = this.postRepository.findAll(p);
		List<Post> allposts = pagePost.getContent();
		List<PostDto> postDtos = allposts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());

		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		logger.info("completed dao call for get All Post");
        
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		logger.info("Initiating dao call for get the Post details with postId:{}", postId);
        Post post = this.postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND + postId, "Post id", postId));
 		logger.info("Completed dao call for get the Post details with postId:{}", postId);

		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		logger.info("Initiating dao call for get the user details with categoryId:{}", categoryId);
        Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId,
						"category", categoryId));

		List<Post> posts = this.postRepository.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
 		logger.info("Completed dao call for get the user details with categoryId:{}", categoryId);

		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Long userId) {
		logger.info("Initiating dao call for get the user details with userId:{}", userId);
        User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userId, "user", userId));

		List<Post> posts = this.postRepository.findAllByUser(user);

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
 		logger.info("Completed dao call for get the user details with userId:{}", userId);
        return postDtos;
	}

	// Search
	@Override
	public List<PostDto> searchPosts(String Keyword) {
		logger.info("Initiating dao call for search Posts");
        List<Post> posts = this.postRepository.findByTitleContaining(Keyword);
        List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		logger.info("Completed dao call for search Posts");
        return postDtos;
	}

}