package com.pegasus.test.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pegasus.test.dto.request.OnCreate;

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
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
