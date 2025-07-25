package com.projectash.gameview.mappers;

import org.mapstruct.Mapper;
import com.projectash.gameview.dtos.UserDto;
import com.projectash.gameview.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserDto toDto(User user);

}
