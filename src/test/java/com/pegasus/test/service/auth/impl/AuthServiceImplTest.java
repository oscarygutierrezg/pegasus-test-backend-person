package com.pegasus.test.service.auth.impl;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

	@InjectMocks
	private AuthServiceImpl authServiceImpl;

	private String userName = "testUser";

	private String password = "xCMbk5083";

	private String authorization ="Basic dGVzdFVzZXI6eENNYms1MDgz";
	@Test
	public void test_validateBasicAuthentication_Should_Return_True_When_Invoked() {
		Assertions.assertTrue(authServiceImpl.validateBasicAuthentication(userName, password, authorization));
	}

	@Test
	public void test_validateBasicAuthentication_Should_Return_False_When_Invoked_With_Param_Password() {
		String passwordFake = "xCMbk50832";
		Assertions.assertFalse(authServiceImpl.validateBasicAuthentication(userName, passwordFake, authorization));
	}

	@Test
	public void test_validateBasicAuthentication_Should_Return_False_When_Invoked_With_Param_UserName() {
		String userNameFake = "xCMbk50832";
		Assertions.assertFalse(authServiceImpl.validateBasicAuthentication(userNameFake, password, authorization));
	}
}
