package com.pegasus.test.service.auth;

public interface AuthService {
	Boolean validateBasicAuthentication(String userName, String password, String basicAuthHeaderValue);

}
