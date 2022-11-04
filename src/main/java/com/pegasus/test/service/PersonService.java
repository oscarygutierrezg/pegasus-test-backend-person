package com.pegasus.test.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pegasus.test.dto.PersonDto;
import com.pegasus.test.dto.PersonRequest;


public interface PersonService {
	PersonDto create(PersonRequest personRequest);

	PersonDto update(UUID uuid, PersonRequest personRequest);

	Page<PersonDto> index(Pageable pageable);

	PersonDto show(UUID uuid);
	
	void delete(UUID uuid);


}
