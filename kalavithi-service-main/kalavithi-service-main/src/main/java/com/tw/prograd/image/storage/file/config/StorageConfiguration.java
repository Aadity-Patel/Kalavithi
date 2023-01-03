package com.tw.prograd.image.storage.file.config;

import com.tw.prograd.image.storage.file.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfiguration {

    @Bean
    CommandLineRunner init(StorageService service) {
        return (args) -> service.init();
    }
}
