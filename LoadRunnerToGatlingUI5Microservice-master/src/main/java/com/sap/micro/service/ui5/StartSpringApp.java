package com.sap.micro.service.ui5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.sap.micro.service.ui5.uploadfile.filedemo.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class StartSpringApp {

	public static void main(String[] args) {
		SpringApplication.run(StartSpringApp.class, args);
	}

}
