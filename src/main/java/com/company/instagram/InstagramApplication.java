package com.company.instagram;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.IOException;


@SpringBootApplication
@OpenAPIDefinition
public class InstagramApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(InstagramApplication.class, args);
    }
}
