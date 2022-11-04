package com.pegasus.test.dto;

import java.util.UUID;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
	
	private UUID id;
	protected String name;
	protected String lastName;
	protected String docNumber;
	protected String address;
	protected String phone;
}
