package com.pegasus.test.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.pegasus.test.model.Person;


@Mapper(
		componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		unmappedTargetPolicy = ReportingPolicy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
		)
public abstract interface PersonMapper {


	public abstract PersonDto toDto(Person Person);

	public abstract Person toModel(PersonRequest Person);

	public  abstract void updateModel(PersonRequest PersonRequest, @MappingTarget Person Person);

}
