package com.example.taskmanager.mapper;


import com.example.taskmanager.dto.everydayTask.EverydayTaskCreationDto;
import com.example.taskmanager.dto.everydayTask.EverydayTaskDto;
import com.example.taskmanager.dto.everydayTask.EverydayTaskUpdateDto;
import com.example.taskmanager.entity.EverydayTask;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EverydayTaskMapper {



    EverydayTask toEntity(EverydayTaskCreationDto everydayTaskCreationDto);


    EverydayTaskDto toDto(EverydayTask everydayTask);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EverydayTask updateEverydayTask(EverydayTaskUpdateDto everydayTaskUpdateDto, @MappingTarget EverydayTask everydayTask);



}
