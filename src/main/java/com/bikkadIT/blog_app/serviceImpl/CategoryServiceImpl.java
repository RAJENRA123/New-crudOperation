package com.bikkadIT.blog_app.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikkadIT.blog_app.constant.AppConstants;
import com.bikkadIT.blog_app.controller.CategoryController;
import com.bikkadIT.blog_app.entity.Category;
import com.bikkadIT.blog_app.entity.User;
import com.bikkadIT.blog_app.exception.ResourceNotFoundException;
import com.bikkadIT.blog_app.payloads.CategoryDto;
import com.bikkadIT.blog_app.payloads.UserDto;
import com.bikkadIT.blog_app.repository.CategoryRepo;
import com.bikkadIT.blog_app.service.CategoryService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	Logger logger=LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;
	
    @Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		logger.info("Initiating dao call for creat Category");
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category saveCategory = categoryRepo.save(category);
		logger.info("Complete dao call for creat Category");
		return this.modelMapper.map(saveCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		logger.info("Initiating dao call for Update the Category details with categoryId:{}", categoryId);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(
				() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId, "Category", categoryId));
		category.setCategoryId(categoryDto.getCategoryId());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = categoryRepo.save(category);
		logger.info("Completed dao call for Update the Category details with categoryId:{}", categoryId);
        return modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		logger.info("Initiating dao call for delete the Category details with categoryId:{}", categoryId);
        Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGPRY_DELETED + categoryId, "Category",
						categoryId));
 		logger.info("Completed dao for delete the Category details with categoryId:{}", categoryId);

	    this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		logger.info("Inititing dao call  for get the Category details with categoryId:{}", categoryId);

		Category category = this.categoryRepo.findById(categoryId).orElseThrow(
				() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId, "Cayegory", categoryId));
 		logger.info("Completed dao call for get the Category details with categoryId:{}", categoryId);

		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		logger.info("Initiating dao call for get all Category details");
        List<Category> allCategory = categoryRepo.findAll();
		List<CategoryDto> list = allCategory.stream()
				.map((categories) -> modelMapper.map(categories, CategoryDto.class)).collect(Collectors.toList());
		logger.info("Completed dao call for get all Category details");

		return list;
	}

}
