package com.cooksys.TwitterMockupAPI.mappers;

import com.cooksys.TwitterMockupAPI.dtos.UserRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.TwitterMockupAPI.dtos.UserResponseDto;
import com.cooksys.TwitterMockupAPI.entities.User;

import java.util.List;

@Mapper(componentModel = "spring", uses= {ProfileMapper.class, CredentialsMapper.class})
public interface UserMapper {

	@Mapping(target="username", source="credentials.username")
	UserResponseDto entityToDto(User user);

	User requestDtoToEntity(UserRequestDto userRequestDto);

	List<UserResponseDto> entitiesToResponseDtos(List<User> users);

}
