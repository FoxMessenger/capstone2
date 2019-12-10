package com.trilogyed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
//@EnableCaching
public class AdminApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApiApplication.class, args);
    }
}
