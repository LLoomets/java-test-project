package com.example.java_test_project.service;

import com.example.java_test_project.model.LogEntry;
import com.example.java_test_project.parser.LogParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return reader.lines()
                    .map(logParser::parseLine)
                    .collect(Collectors.toList());
        }
    }

    public Map<Integer, Long> countHttpCodes(String filename) throws IOException {
        List<LogEntry> entries = readLogFile(filename);
        return entries.stream().collect(Collectors.groupingBy(LogEntry::getHttpCode, TreeMap::new, Collectors.counting()));
    }

    public Map<String, Long> averageRequestDuration(String filename) throws IOException {
        List<LogEntry> entries = readLogFile(filename);

        Map<String, Long> avgDurations = entries.stream()
                .collect(Collectors.groupingBy(
                        LogEntry::getPage,
                        Collectors.collectingAndThen(
                                Collectors.averagingInt(LogEntry::getDuration),
                                Math::round
                        )
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

        Map<String, Long> errorCount = entries.stream().
                filter(entry -> entry.getHttpCode() == errorCode)
                .collect(Collectors.groupingBy(
                        LogEntry::getPage,
                        Collectors.counting()
                ));

        return errorCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e2,
                        LinkedHashMap::new
                ));
    }
}
