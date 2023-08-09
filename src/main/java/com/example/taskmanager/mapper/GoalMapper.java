package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.goal.GoalCreationDto;
import com.example.taskmanager.dto.goal.GoalDto;
import com.example.taskmanager.dto.goal.GoalUpdateDto;
import com.example.taskmanager.entity.Goal;
import org.mapstruct.*;

import java.time.LocalDate;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GoalMapper {



    @Mapping(source = "dueTo", target = "dueDate")
    Goal toEntity(GoalCreationDto goalCreationDto);


    GoalDto toDto(Goal goal);




    @Mapping(source = "dueTo", target = "dueDate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Goal updateGoal(GoalUpdateDto goalUpdateDto, @MappingTarget Goal goal);


}
