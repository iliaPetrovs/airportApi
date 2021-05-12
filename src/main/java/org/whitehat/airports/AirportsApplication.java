package org.whitehat.airports;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AirportsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirportsApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {

		return new OpenAPI()
				.info(new Info()
						.title("Airports")
						.version(appVersion)
						.description("28,000 airports")
				)
				.addServersItem(new Server().url("http://localhost:8080/"))
				.addServersItem(new Server().url("https://api.whitehatcoaches.org.uk/"));
	}
}

