package com.blog.services;

import java.util.List;

import com.blog.dto.CategoryDto;

public interface CategoryService {

	//create
	public CategoryDto createCategory(CategoryDto categoryDto);
	//update
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	//delete
	public String  deleteCategory(Integer categoryId);
	//get
	public CategoryDto getCategory(Integer categoryId);
	//getAll
	List<CategoryDto> getcategories();
 }
