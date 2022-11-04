package com.pegasus.test.util;

import java.util.UUID;

import com.github.javafaker.Faker;
import com.pegasus.test.dto.PersonDto;
import com.pegasus.test.dto.PersonRequest;
import com.pegasus.test.model.Person;

public class PersonUtil {
	
	public Faker faker = new Faker();
	
	
	public Person creatModel(Person p) {
		p.setId(UUID.randomUUID());
		return p;
	}

	public Person createPerson() {
		Person person = new Person();
		person.setAddress(faker.address().fullAddress());
		person.setDocNumber(faker.idNumber().valid());
		person.setLastName(faker.name().lastName());
		person.setName(faker.name().firstName());
		person.setPhone(faker.phoneNumber().cellPhone());
		person.setId(UUID.randomUUID());
		return person;
	}

	public Person toModel(PersonRequest personDto) {
		Person person = new Person();
		person.setAddress(personDto.getAddress());
		person.setDocNumber(personDto.getDocNumber());
		person.setLastName(personDto.getLastName());
		person.setName(personDto.getName());
		person.setPhone(personDto.getPhone());
		return person;
	}

	public PersonDto toDto(Person personDto) {
		return  PersonDto.builder()
				.address(personDto.getAddress())
				.docNumber(personDto.getDocNumber())
				.id(personDto.getId())
				.lastName(personDto.getLastName())
				.name(personDto.getName())
				.phone(personDto.getPhone())
				.build();
	}


	public PersonRequest createPersonRequest() {
		return  PersonRequest.builder()
				.address(faker.address().fullAddress())
				.docNumber(faker.idNumber().valid())
				.lastName(faker.name().lastName())
				.name(faker.name().firstName())
				.phone(faker.phoneNumber().cellPhone())
				.build();
	}

}
