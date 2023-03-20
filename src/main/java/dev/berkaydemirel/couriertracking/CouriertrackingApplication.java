package dev.berkaydemirel.couriertracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync(proxyTargetClass = true)
@EnableCaching
@SpringBootApplication
public class CouriertrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouriertrackingApplication.class, args);
    }

}
