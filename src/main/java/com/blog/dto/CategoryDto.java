package com.blog.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	private Integer categoryId;
	@NotEmpty
	@Size(min = 4, message = "Category Title must be min  of 4 Character")
	private String categoryTitle;
	@NotEmpty
	@Size(min = 10, message = "Category Description must be min  of 4 Character")
	private String categoryDecription;
	
}
