package com.example.java_test_project.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service

public class LogService {

    public List<String> readLogFile(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource("logs/" + fileName);
        return Files.readAllLines(resource.getFile().toPath());
    }
}
