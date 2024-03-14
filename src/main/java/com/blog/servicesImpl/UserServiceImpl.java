package com.blog.servicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import com.blog.exceptions.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.Dao.UserRepo;
import com.blog.dto.UserDto;
import com.blog.entity.User;
import com.blog.services.UserService;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) throws Exception  {
		
		Pattern pattern= Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
		
		java.util.regex.Matcher matcher= pattern.matcher(userDto.getEmail());
		
		if(!matcher.matches()) {
			
			throw new EmailAlreadyExistException("Enter correct email");
		}
		
		if(fetchUserByEmailId(userDto.getEmail()).isPresent()) {
			throw new EmailAlreadyExistException("Email already exist");
		}
		
		User user = this.dtoTouser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateuser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updateUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updateUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepo.findAll();
		List<UserDto> UserDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return UserDtos;
	}

	@Override	
	public String deleteUser(Integer userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User ID", userId));
		userRepo.deleteById(userId);
		return "User deleted  Success";
	}

//	private User dtoTouser(UserDto userDto) {
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
//		return user;
//	}
	
	
	// we use model Mapper  thats why we comment all the things
	
	private User dtoTouser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}

	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		
		return userDto;
	}

	@Override
	public Optional<User> fetchUserByEmailId(String tempEmail) {

		return userRepo.findByEmail(tempEmail);
	}
}
