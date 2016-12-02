package org.ccps.navigation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(org.ccps.navigation.config.ApplicationConfiguration.class)
@EnableAutoConfiguration
public class NavigationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NavigationApplication.class, args);
    }
}
