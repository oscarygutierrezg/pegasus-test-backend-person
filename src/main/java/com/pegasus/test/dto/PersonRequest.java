package com.pegasus.test.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pegasus.test.dto.request.OnCreate;

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
public class PersonRequest {
	
	@NotNull(message = "Name is mandatory", groups = OnCreate.class)
	@NotEmpty(message = "Name must not be empty", groups = OnCreate.class)
	protected String name;
	@NotNull(message = "LastName is mandatory", groups = OnCreate.class)
	@NotEmpty(message = "LastName must not be empty", groups = OnCreate.class)
	protected String lastName;
	@NotNull(message = "DocNumber is mandatory", groups = OnCreate.class)
	@NotEmpty(message = "DocNumber must not be empty", groups = OnCreate.class)
	protected String docNumber;
	@NotNull(message = "Address is mandatory", groups = OnCreate.class)
	@NotEmpty(message = "Address must not be empty", groups = OnCreate.class)
	protected String address;
	@NotNull(message = "Phone is mandatory", groups = OnCreate.class)
	@NotEmpty(message = "Phone must not be empty", groups = OnCreate.class)
	protected String phone;
	
}
