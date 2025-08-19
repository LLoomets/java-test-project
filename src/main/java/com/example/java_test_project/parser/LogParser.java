package com.example.java_test_project.parser;


import com.example.java_test_project.model.LogEntry;
import org.springframework.stereotype.Component;

@Component
public class LogParser {

    public LogEntry parseLine(String line) {
        try {
            String[] parts = line.split("\\s+");

            String page = parts[6].replace("\"", "");
            if (page.startsWith("/")) {
                page = page.substring(1);
            }
            int httpCode = Integer.parseInt(parts[7]);
            int duration = Integer.parseInt(parts[9]);

            return new LogEntry(page, httpCode, duration);
        } catch (Exception e) {
            System.err.println("failed to parse line: " + line);
            return null;
        }
    }
}
