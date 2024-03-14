package com.blog.services;

import java.util.List;
import java.util.Optional;

import com.blog.dto.UserDto;
import com.blog.entity.User;

public interface UserService {

	UserDto createUser(UserDto user) throws Exception;

	UserDto updateuser(UserDto user, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUsers();

	String deleteUser(Integer userId);

	Optional<User> fetchUserByEmailId(String tempEmail);

}
