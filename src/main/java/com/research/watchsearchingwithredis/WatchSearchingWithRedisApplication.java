package com.research.watchsearchingwithredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class WatchSearchingWithRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(WatchSearchingWithRedisApplication.class, args);
    }

}
