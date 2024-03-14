package com.blog.controllers;

import java.util.List;


import javax.validation.Valid;

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

import com.blog.dto.ApiResponce;
import com.blog.dto.UserDto;
import com.blog.services.UserService;

@RestController("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(path = "/createUser", produces = "application/JSON", consumes = "application/JSON")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) throws Exception {
		
//		if (userDto.getEmail() != null && !"".equals(userDto.getEmail())) {
//			if (userService.fetchUserByEmailId(userDto.getEmail())!=null) {
//				throw new EmailAlreadyExistException("User with " + userDto.getEmail() + " is already Exist");
//			}
//		}

//		if (userDto.getEmail() != null && !"".equals(userDto.getEmail())) {
//			Optional<User> userEmial = userService.fetchUserByEmailId(userDto.getEmail());
//			if (userEmial != null) {
//				throw new EmailAlreadyExistException("User with " + userDto.getEmail() + " is already Exist");
//			}
//		}

		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);

	}

	@PutMapping("/updateUser/{userId}")
	public ResponseEntity<UserDto> userUpdate(@RequestBody UserDto userDto, @PathVariable("userId") Integer userId) {
		UserDto updatedUser = this.userService.updateuser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<ApiResponce> deleteUser(@PathVariable("userId") Integer userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponce>(new ApiResponce("User Deleted Successfully", true), HttpStatus.OK);
	}

	@GetMapping("/AllUsers")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}

	@GetMapping("/GetUsers/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	

}

