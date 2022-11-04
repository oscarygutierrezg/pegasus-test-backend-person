package com.pegasus.test.service.impl;


import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.github.javafaker.Faker;
import com.pegasus.test.dto.PersonDto;
import com.pegasus.test.dto.PersonMapper;
import com.pegasus.test.dto.PersonRequest;
import com.pegasus.test.model.Person;
import com.pegasus.test.repository.PersonRepository;


@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

	@InjectMocks
	private PersonServiceImpl personServiceImpl;

	@Mock
	private PersonRepository personRepository;
	@Spy
	private PersonMapper personMapper;

	private Faker faker = new Faker();


	@Test
	public void test_Create_Should_CreatedPerson_When_Invoked() {
		Mockito.when(personRepository.save(Mockito.any(Person.class))).thenAnswer(p -> creatModel((Person) p.getArguments()[0]));
		Mockito.when(personMapper.toDto(Mockito.any(Person.class))).thenAnswer(p -> toDto((Person) p.getArguments()[0]));
		Mockito.when(personMapper.toModel(Mockito.any(PersonRequest.class))).thenAnswer(p -> toModel((PersonRequest) p.getArguments()[0]));
		PersonRequest personRequest = createPersonRequest();
		PersonDto personDto = personServiceImpl.create(personRequest);
		
		Assertions.assertNotNull(personDto);
		Assertions.assertNotNull(personDto.getId());
		
		Mockito.verify(personRepository, Mockito.times(1)).save(Mockito.any());
		Mockito.verify(personMapper, Mockito.times(1)).toDto(Mockito.any());
		Mockito.verify(personMapper, Mockito.times(1)).toModel(Mockito.any());
	}
	
	
	@Test
	public void test_Update_Should_UpdatedPerson_When_Invoked() {
		Mockito.when(personRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(createPerson()));
		Mockito.when(personRepository.save(Mockito.any(Person.class))).thenAnswer(p -> creatModel((Person) p.getArguments()[0]));
		Mockito.when(personMapper.toDto(Mockito.any(Person.class))).thenAnswer(p -> toDto((Person) p.getArguments()[0]));
		Mockito.doNothing().when(personMapper).updateModel(
				Mockito.any(PersonRequest.class),
				Mockito.any(Person.class));
		PersonRequest personRequest = createPersonRequest();
		PersonDto personDto = personServiceImpl.update(UUID.randomUUID(), personRequest);
		
		Assertions.assertNotNull(personDto);
		
		Mockito.verify(personRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
		Mockito.verify(personRepository, Mockito.times(1)).save(Mockito.any());
		Mockito.verify(personMapper, Mockito.times(1)).toDto(Mockito.any());
		Mockito.verify(personMapper, Mockito.times(1)).updateModel(Mockito.any(),Mockito.any());
	}
	
	@Test
	public void test_Delete_Should_DeletedPerson_When_Invoked() {
		Mockito.when(personRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(createPerson()));
		Mockito.doNothing().when(personRepository).deleteById(
				Mockito.any(UUID.class));
		personServiceImpl.delete(UUID.randomUUID());
		
		
		Mockito.verify(personRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
		Mockito.verify(personRepository, Mockito.times(1)).deleteById(Mockito.any(UUID.class));
	}
	
	@Test
	public void test_Show_Should_ReturnPerson_When_Invoked() {
		Mockito.when(personRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(createPerson()));
		Mockito.when(personMapper.toDto(Mockito.any(Person.class))).thenAnswer(p -> toDto((Person) p.getArguments()[0]));
		PersonDto personDto = personServiceImpl.show(UUID.randomUUID());
		
		Assertions.assertNotNull(personDto);
		Assertions.assertNotNull(personDto.getId());
		
		Mockito.verify(personRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
		Mockito.verify(personMapper, Mockito.times(1)).toDto(Mockito.any());
	}
	

	@Test
	public void test_Show_Should_ReturnEntityNotFoundException_When_Invoked() {
		Mockito.when(personRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());
		
		EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
			 personServiceImpl.show(UUID.randomUUID());
		    });
		
		Assertions.assertNotNull(exception);
		Assertions.assertNotNull(exception.getMessage());
		
		Mockito.verify(personRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
		
	}
	
	@Test
	public void test_index_Should_ReturnPagePerson_When_Invoked() {
		Page<Person> page = new PageImpl(Arrays.asList(createPerson(),createPerson(),createPerson()));
		
		Mockito.when(personRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
		Mockito.when(personMapper.toDto(Mockito.any(Person.class))).thenAnswer(p -> toDto((Person) p.getArguments()[0]));
		Page<PersonDto> pageDto = personServiceImpl.index(PageRequest.of(0,3));
		
		Assertions.assertNotNull(pageDto);
		Assertions.assertNotNull(pageDto.getContent());
		Assertions.assertEquals(3, pageDto.getContent().size());
		
		Mockito.verify(personRepository, Mockito.times(1)).findAll(Mockito.any(Pageable.class));
		Mockito.verify(personMapper, Mockito.times(3)).toDto(Mockito.any());
	}

	private Person creatModel(Person p) {
		p.setId(UUID.randomUUID());
		return p;
	}
	
	private Person createPerson() {
		Person person = new Person();
		person.setAddress(faker.address().fullAddress());
		person.setDocNumber(faker.idNumber().valid());
		person.setLastName(faker.name().lastName());
		person.setName(faker.name().firstName());
		person.setPhone(faker.phoneNumber().cellPhone());
		person.setId(UUID.randomUUID());
		return person;
	}

	private Person toModel(PersonRequest personDto) {
		Person person = new Person();
		person.setAddress(personDto.getAddress());
		person.setDocNumber(personDto.getDocNumber());
		person.setLastName(personDto.getLastName());
		person.setName(personDto.getName());
		person.setPhone(personDto.getPhone());
		return person;
	}

	private PersonDto toDto(Person personDto) {
		PersonDto person = new PersonDto();
		person.setAddress(personDto.getAddress());
		person.setDocNumber(personDto.getDocNumber());
		person.setId(personDto.getId());
		person.setLastName(personDto.getLastName());
		person.setName(personDto.getName());
		person.setPhone(personDto.getPhone());
		return person;
	}


	private PersonRequest createPersonRequest() {
		PersonRequest person = new PersonRequest();
		person.setAddress(faker.address().fullAddress());
		person.setDocNumber(faker.idNumber().valid());
		person.setLastName(faker.name().lastName());
		person.setName(faker.name().firstName());
		person.setPhone(faker.phoneNumber().cellPhone());
		return person;
	}
}
