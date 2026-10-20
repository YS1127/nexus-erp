package com.nexus.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nexus.erp")
public class NexusErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexusErpApplication.class, args);
    }
}
