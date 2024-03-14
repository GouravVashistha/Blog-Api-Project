package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blog.config.AppConstants;
import com.blog.dto.ApiResponce;
import com.blog.dto.CategoryDto;
import com.blog.exceptions.DetailsNotFoundException;
import com.blog.services.CategoryService;


@RestController("./api/Category")

public class CatgeoryController {
	@Autowired
	private CategoryService categoryService;
	
	// Create
	
	@PostMapping("/CreateCategory")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) throws Exception{
		if(categoryDto.getCategoryTitle()!=null && !"".equals(categoryDto.getCategoryTitle())) {
			CategoryDto createCategory =this.categoryService.createCategory(categoryDto);
			return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
		}
		else {
			throw new DetailsNotFoundException(AppConstants.DETAIL_NOT_FOUND_eXCEPTION);
			
		}
		
	}
	// update
	
	@PutMapping("/updateCategory/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer catId){
		CategoryDto updateCategory =this.categoryService.updateCategory(categoryDto,catId);
		return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
	}
	// Delete
	
	@DeleteMapping("/deleteCategory/{CatId}")
	public ResponseEntity<ApiResponce> deleteCategory(@PathVariable("CatId") Integer CatId) {
		this.categoryService.deleteCategory(CatId);
		return new ResponseEntity<ApiResponce>(new ApiResponce("Category Is Deleted Successfully ! !", true), HttpStatus.OK);
	}
	//get
	
	@GetMapping("/getCategoryByID/{CatId}")
	public ResponseEntity<CategoryDto> getcategory(@PathVariable("CatId") Integer catId){
		return ResponseEntity.ok(this.categoryService.getCategory(catId));
	}
	// getall
	
	@GetMapping("/getAllCatgegory/")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		return ResponseEntity.ok(this.categoryService.getcategories());
	}

}
