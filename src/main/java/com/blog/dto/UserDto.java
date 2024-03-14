package com.blog.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private Integer id;
	@NotEmpty
	@Size(min = 4, message = "User must be min  of 4 Character")
	private String name;
	
	@Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",message = "Email  is not valide..!!")
	private String email;
	
	@NotEmpty
	@Size(min = 3, max = 10 ,message = "Password must be at least 3 digits and max is 10..!!")
	private String password;
	
	@NotEmpty
	private String about;

}
