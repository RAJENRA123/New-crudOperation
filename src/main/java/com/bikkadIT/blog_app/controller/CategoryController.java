package com.bikkadIT.blog_app.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkadIT.blog_app.constant.AppConstants;
import com.bikkadIT.blog_app.payloads.CategoryDto;
import com.bikkadIT.blog_app.service.CategoryService;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api")
public class CategoryController {
	
	Logger logger=LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;
     
	/**
	 * @author Rajendra
	 * @apiNote This Api is used for create category
	 * @param categoryDto
	 * @return
	 */
	
	@PostMapping("/category")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		logger.info("Initiated requst for creat Category");
		CategoryDto createCategory = categoryService.createCategory(categoryDto);
		logger.info("Completed requst for creat Category");
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}
     
	/**
       * @apiNote This Api is used for update category
       * @param categoryDto
       * @param categoryId
       * @return
       */
	
	@PutMapping("/category/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("id") Integer categoryId) {
		logger.info("Initiated request for Update the Category details with categoryId:{}", categoryId);
		CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
		logger.info("Completed request for Update the Category details with categoryId:{}", categoryId);
        return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
	}
	/**
	 * @apiNote This Api is used for delete category
	 * @param categoryId
	 * @return
	 */
	
	@DeleteMapping("category/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Integer categoryId){
		logger.info("Initiated request for delete the Category details with categoryId:{}", categoryId);
        categoryService.deleteCategory(categoryId);
 		logger.info("Completed request for delete the Category details with categoryId:{}", categoryId);
         return new ResponseEntity<String>(AppConstants.USER_DELETED,HttpStatus.OK);
	}
	
	/**
	 * @apiNote This Api is used for get category by Id
	 * @param categoryId
	 * @return
	 */
	
	@GetMapping("category/{id}")
	public ResponseEntity<CategoryDto>getSingleCategory(@PathVariable("id") Integer categoryId){
		logger.info("Initiated request for get the Category details with categoryId:{}", categoryId);
        CategoryDto category = categoryService.getCategory(categoryId);
 		logger.info("Completed request for get the Category details with categoryId:{}", categoryId);
 		return new ResponseEntity<CategoryDto>(category,HttpStatus.OK);
		
	}
	
	/**
	 * @apiNote This Api is used for get all Category
	 * @return
	 */
	
	@GetMapping("category")
	public ResponseEntity<List<CategoryDto>>getAllCategory(){
		logger.info("Initiate requst for get all Category details");
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		logger.info("Completed requst for get all Category details");
        return new ResponseEntity<>(allCategory,HttpStatus.OK);
	
	}
	
	
}
