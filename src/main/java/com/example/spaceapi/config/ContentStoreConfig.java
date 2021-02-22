package com.example.spaceapi.config;

import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Configuration
@EnableFilesystemStores
public class ContentStoreConfig {

    @Bean
    File fileSystemRoot() {
        try {
            return Files.createTempDirectory("").toFile();
        } catch (IOException ex) {
            return null;
        }
    }

    @Bean
    FileSystemResourceLoader fileSystemResourceLoader() {
        return new FileSystemResourceLoader(fileSystemRoot().getAbsolutePath());
    }

}
