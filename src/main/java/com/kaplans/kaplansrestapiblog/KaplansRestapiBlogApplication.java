package com.kaplans.kaplansrestapiblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-kaplan.properties")
public class KaplansRestapiBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(KaplansRestapiBlogApplication.class, args);
    }

}
