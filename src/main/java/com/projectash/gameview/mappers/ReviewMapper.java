package com.projectash.gameview.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import com.projectash.gameview.dtos.ReviewRequestDto;
import com.projectash.gameview.dtos.ReviewDto;
import com.projectash.gameview.entities.Review;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Review toEntity(ReviewRequestDto dto);

    @Mapping(target = "username", ignore = true) // setting username in service
    ReviewDto toDto(Review entity);
    // ensures that when updating, null values do not override existing values
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ReviewRequestDto dto, @MappingTarget Review entity);

}
