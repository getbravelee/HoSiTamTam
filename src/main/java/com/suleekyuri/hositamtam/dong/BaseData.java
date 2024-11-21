package com.suleekyuri.hositamtam.dong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@Profile({"dev"})
@ConditionalOnProperty(name = "data.loading.dong.enabled", havingValue = "true")
public class BaseData {

    @Value("${public.data.key}")
    private String apiKey;

    @Bean
    CommandLineRunner initData(DataService dataService) {
        return args -> {
            dataService.callApiAndSaveData(apiKey);

            log.info("BASE_DATA SUCCESS!");
        };
    }
}
