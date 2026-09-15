package com.example.TurnKeyGo_DiscoveryServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class TurnKeyGoDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurnKeyGoDiscoveryServerApplication.class, args);
	}

}
