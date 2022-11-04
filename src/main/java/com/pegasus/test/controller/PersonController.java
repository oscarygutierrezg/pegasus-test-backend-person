package com.pegasus.test.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pegasus.test.dto.PersonDto;
import com.pegasus.test.dto.PersonRequest;
import com.pegasus.test.dto.request.OnCreate;
import com.pegasus.test.service.PersonService;

import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "persons")
@RestController
@RequestMapping(value = "/v1/person")
@CrossOrigin
@Validated
public class PersonController {

	@Autowired
	private PersonService personService;

	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			) public ResponseEntity<PersonDto> createPerson(
					@Validated(OnCreate.class) @RequestBody  PersonRequest personRequest) {

		PersonDto dto = personService.create(personRequest);
		return  ResponseEntity.created(URI.create("/v1/person/" + dto.getId()))
				.body(dto);
	}

	@PutMapping(
			value = "/{uuid}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<PersonDto>  updatePerson(@PathVariable(value = "uuid") UUID uuid,
			@Validated @RequestBody  PersonRequest personRequest) {
		return  ResponseEntity.ok().body(personService.update(uuid, personRequest));
	}



	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Page<PersonDto>>  getAllPersons(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size
			) {
		return ResponseEntity.ok().body(personService.index(PageRequest.of(page,size)));
	}

	@GetMapping(
			value = "/{uuid}",
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public  ResponseEntity<PersonDto>  showPerson(
			@PathVariable(value = "uuid") UUID uuid) {
		return  ResponseEntity.ok().body(personService.show(uuid));
	}
	
	@DeleteMapping(
			value = "/{uuid}",
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public  ResponseEntity<PersonDto>  deletePerson(
			@PathVariable(value = "uuid") UUID uuid) {
		personService.delete(uuid);
		return  ResponseEntity.noContent().build();
	}
	
	


}
