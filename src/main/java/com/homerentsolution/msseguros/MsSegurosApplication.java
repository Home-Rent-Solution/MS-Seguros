package com.homerentsolution.msseguros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class MsSegurosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsSegurosApplication.class, args);
    }

}
