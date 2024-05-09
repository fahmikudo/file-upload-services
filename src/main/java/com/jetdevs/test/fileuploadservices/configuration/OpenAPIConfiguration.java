package com.jetdevs.test.fileuploadservices.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI sajiinOpenApi() {
        Contact contact = new Contact();
        contact.setEmail("fahmi.hidayatullah12@gmail.com");
        contact.setName("Fahmi Kudo");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
        Info info = new Info()
                .title("File Upload Services")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage all requirements file upload services.")
                .termsOfService("https://jetdevs.com/")
                .license(mitLicense);

        return new OpenAPI().info(info);
    }

}
