package com.pegasus.test.service.impl;

import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pegasus.test.dto.PersonDto;
import com.pegasus.test.dto.PersonMapper;
import com.pegasus.test.dto.PersonRequest;
import com.pegasus.test.model.Person;
import com.pegasus.test.repository.PersonRepository;
import com.pegasus.test.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private PersonMapper personMapper;

	@Override
	public PersonDto create(PersonRequest personRequest) {
		return personMapper.toDto(personRepository.save(personMapper.toModel(personRequest)));
	}

	@Override
	public PersonDto update(UUID uuid, PersonRequest personRequest) {
		Person p = findById(uuid);
		personMapper.updateModel(personRequest, p);
		return personMapper.toDto(personRepository.save(p));
		
	}

	@Override
	public Page<PersonDto> index(Pageable pageable) {
		return personRepository.findAll(pageable).map(personMapper::toDto);
	}

	@Override
	public PersonDto show(UUID uuid) {
		Person p = findById(uuid);
		return personMapper.toDto(p);
	}

	@Override
	public void delete(UUID uuid) {
		Person p = findById(uuid);
		personRepository.deleteById(p.getId());
	}
	
	private  Person findById(UUID uuid) {
		return personRepository.findById(uuid).orElseThrow(
				() -> new  EntityNotFoundException(
		                "Person with UUID "+uuid+" does not exist."
			            ));
		
	}

}
