package com.example.taskmanager.mapper;


import com.example.taskmanager.dto.user.UserCreationDto;
import com.example.taskmanager.dto.user.UserDto;
import com.example.taskmanager.dto.user.UserUpdateDto;
import com.example.taskmanager.entity.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto toDto(User user);


    User toEntity(UserCreationDto userCreationDto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User userUpdate(UserUpdateDto userUpdateDto, @MappingTarget User user);


}
