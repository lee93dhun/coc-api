package com.lee93.coc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class CocApplication {
	private static final Logger logger = LoggerFactory.getLogger(CocApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CocApplication.class, args);
	}

	@Bean
	CommandLineRunner testDatabaseConnection(DataSource dataSource){
		return args -> {
			try {
				dataSource.getConnection().close();
				logger.info(" :: DATABASE CONNECTION TEST SUCCESSFUL");
			} catch (Exception e){
				logger.error(" :: DATABASE CONNECTION TEST FAILED", e);
			}
		};
	}

}
