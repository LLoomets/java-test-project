package com.example.java_test_project.service;

import com.example.java_test_project.model.LogEntry;
import com.example.java_test_project.parser.LogParser;
import org.apache.juli.logging.Log;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class LogService {

    private LogParser logParser;

    public LogService(LogParser logParser) {
        this.logParser = logParser;
    }

    public List<LogEntry> readLogFile(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource("logs/" + fileName);
        List<String> lines = Files.readAllLines(resource.getFile().toPath());

        List<LogEntry> entries = new ArrayList<>();
        for (String line : lines) {
            LogEntry entry = logParser.parseLine(line);
            if (entry != null) {
                entries.add(entry);
            }
        }
        return entries;
    }

    public Map<Integer, Long> countHttpCodes(String filename) throws IOException {
        List<LogEntry> entries = readLogFile(filename);
        return entries.stream().collect(Collectors.groupingBy(LogEntry::getHttpCode, Collectors.counting()));
    }
}
