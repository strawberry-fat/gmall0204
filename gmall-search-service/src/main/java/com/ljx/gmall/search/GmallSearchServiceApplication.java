package com.ljx.gmall.search;

import io.searchbox.client.JestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class GmallSearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallSearchServiceApplication.class, args);
    }

}
