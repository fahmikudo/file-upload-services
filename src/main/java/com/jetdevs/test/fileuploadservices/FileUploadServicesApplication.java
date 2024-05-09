package com.jetdevs.test.fileuploadservices;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class FileUploadServicesApplication {

	private final Path root = Paths.get("files");


	public static void main(String[] args) {
		SpringApplication.run(FileUploadServicesApplication.class, args);
	}

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(root);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

}
