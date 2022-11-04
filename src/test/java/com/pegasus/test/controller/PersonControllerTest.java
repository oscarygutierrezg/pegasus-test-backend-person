package com.pegasus.test.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.pegasus.test.PersonApplication;
import com.pegasus.test.dto.PersonDto;
import com.pegasus.test.dto.PersonRequest;
import com.pegasus.test.model.Person;
import com.pegasus.test.repository.PersonRepository;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersonApplication.class)
public class PersonControllerTest {

	private static final String AUTHORIZATION = "Authorization";
	private static final String BASIC = "Basic dGVzdFVzZXI6eENNYms1MDgz";

	@Autowired
	private MockMvc mockMvc;	
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private ObjectMapper objectMapper;
	
	private Faker faker = new Faker();
	
	@Test
	public void test_Create_Should_CreatedPerson_When_Invoked() throws JsonProcessingException, Exception {
		PersonRequest personRequest = createPersonRequest();
		
		ResultActions res =    mockMvc.perform(
	            MockMvcRequestBuilders.post("/v1/person")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(personRequest))
	                .accept(MediaType.APPLICATION_JSON)
	                .header(AUTHORIZATION, BASIC)
	        )
	            .andDo(MockMvcResultHandlers.print())
	            .andExpectAll(
	                    MockMvcResultMatchers.status().isCreated()
	                
	            );
		
		Assertions.assertNotNull(res);
		Assertions.assertNotNull(res.andReturn());
		Assertions.assertNotNull(res.andReturn().getResponse());
		Assertions.assertNotNull(res.andReturn().getResponse().getContentAsString());
		PersonDto personDto = objectMapper.readValue(res.andReturn().getResponse().getContentAsString(), PersonDto.class);
		Assertions.assertNotNull(personDto.getId());
		
		Person person = personRepository.findById(personDto.getId()).get();
		
		Assertions.assertNotNull(person);
		Assertions.assertTrue(person.getDocNumber().equals(personDto.getDocNumber()));
		personRepository.deleteById(personDto.getId());
		
	}
	
	@Test
	public void test_Update_Should_Updateerson_When_Invoked() throws JsonProcessingException, Exception {
		Person person = new Person();
		personRepository.save(person);
		PersonRequest personRequest = createPersonRequest();
		
		ResultActions res =    mockMvc.perform(
	            MockMvcRequestBuilders.put("/v1/person/"+person.getId())
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(personRequest))
	                .accept(MediaType.APPLICATION_JSON)
	                .header(AUTHORIZATION, BASIC)
	        )
	            .andDo(MockMvcResultHandlers.print())
	            .andExpectAll(
	                    MockMvcResultMatchers.status().isOk()
	                
	            );
		
		Assertions.assertNotNull(res);
		Assertions.assertNotNull(res.andReturn());
		Assertions.assertNotNull(res.andReturn().getResponse());
		Assertions.assertNotNull(res.andReturn().getResponse().getContentAsString());
		PersonDto personDto = objectMapper.readValue(res.andReturn().getResponse().getContentAsString(), PersonDto.class);
		Assertions.assertNotNull(personDto.getId());
		
		person = personRepository.findById(personDto.getId()).get();
		
		Assertions.assertNotNull(person);
		Assertions.assertTrue(person.getDocNumber().equals(personDto.getDocNumber()));
		personRepository.deleteById(personDto.getId());
		
	}
	
	@Test
	public void test_Show_Should_ShowPerson_When_Invoked() throws JsonProcessingException, Exception {
		Person person = createPerson();
		personRepository.save(person);
		
		
		
		ResultActions res =    mockMvc.perform(
	            MockMvcRequestBuilders.get("/v1/person/"+person.getId())
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .header(AUTHORIZATION, BASIC)
	        )
	            .andDo(MockMvcResultHandlers.print())
	            .andExpectAll(
	                    MockMvcResultMatchers.status().isOk()
	                
	            );
		
		Assertions.assertNotNull(res);
		Assertions.assertNotNull(res.andReturn());
		Assertions.assertNotNull(res.andReturn().getResponse());
		Assertions.assertNotNull(res.andReturn().getResponse().getContentAsString());
		PersonDto personDto = objectMapper.readValue(res.andReturn().getResponse().getContentAsString(), PersonDto.class);
		Assertions.assertTrue(person.getDocNumber().equals(personDto.getDocNumber()));
		personRepository.deleteById(personDto.getId());
	}
	
	@Test
	public void test_Index_Should_ShowPagePerson_When_Invoked() throws JsonProcessingException, Exception {
		personRepository.save(createPerson());
		personRepository.save(createPerson());
		personRepository.save(createPerson());
		personRepository.save(createPerson());
		
		mockMvc.perform(
	            MockMvcRequestBuilders.get("/v1/person")
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .header(AUTHORIZATION, BASIC)
	        )
	            .andDo(MockMvcResultHandlers.print())
	            .andExpectAll(
	                    MockMvcResultMatchers.status().isOk(),
	                    MockMvcResultMatchers.jsonPath("$.totalElements").value(4),
	                    MockMvcResultMatchers.jsonPath("$.totalPages").value(1)
	                    
	                    
	                
	            );
		
	}
	
	@Test
	public void test_Delete_Should_DeletePerson_When_Invoked() throws JsonProcessingException, Exception {
		Person person = createPerson();
		personRepository.save(person);
		
		mockMvc.perform(
	            MockMvcRequestBuilders.delete("/v1/person/"+person.getId())
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .header(AUTHORIZATION, BASIC)
	        )
	            .andDo(MockMvcResultHandlers.print())
	            .andExpectAll(
	                    MockMvcResultMatchers.status().isNoContent()
	                
	            );
	}
	
	private Person createPerson() {
		Person person = new Person();
		person.setAddress(faker.address().fullAddress());
		person.setDocNumber(faker.idNumber().valid());
		person.setLastName(faker.name().lastName());
		person.setName(faker.name().firstName());
		person.setPhone(faker.phoneNumber().cellPhone());
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
