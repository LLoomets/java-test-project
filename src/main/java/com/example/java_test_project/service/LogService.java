package com.example.java_test_project.service;

import com.example.java_test_project.model.LogEntry;
import com.example.java_test_project.parser.LogParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
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
        return entries.stream().collect(Collectors.groupingBy(LogEntry::getHttpCode, TreeMap::new, Collectors.counting()));
    }

    public Map<String, Double> averageRequestDuration(String filename) throws IOException {
        List<LogEntry> entries = readLogFile(filename);

        Map<String, Double> avgDurations = entries.stream()
                .collect(Collectors.groupingBy(
                        LogEntry::getPage,
                        Collectors.averagingInt(LogEntry::getDuration)
                ));

        return avgDurations.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e2,
                        LinkedHashMap::new
                ));
    }

    public Map<String, Long> errorCountPerPage(String filename, int errorCode) throws IOException {
        List<LogEntry> entries = readLogFile(filename);
        return entries.stream().filter(entry -> entry.getHttpCode() == errorCode).collect(Collectors.groupingBy(LogEntry::getPage, Collectors.counting()));
    }
}
