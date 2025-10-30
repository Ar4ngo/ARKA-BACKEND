package com.arka.AceleraTI_ArkaStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AceleraTiArkaStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(AceleraTiArkaStoreApplication.class, args);
    }
}
