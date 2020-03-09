package com.example.springboot.titan.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class TitanEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TitanEurekaServerApplication.class, args);
    }

}
