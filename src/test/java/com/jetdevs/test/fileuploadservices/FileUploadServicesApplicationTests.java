package com.jetdevs.test.fileuploadservices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class FileUploadServicesApplicationTests {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {

		String password = "s3cr3t";
		String encode = passwordEncoder.encode(password);

		System.out.println(encode);


	}

}
