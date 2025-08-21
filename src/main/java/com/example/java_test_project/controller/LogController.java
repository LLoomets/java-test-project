package com.example.java_test_project.controller;

import com.example.java_test_project.model.LogEntry;
import com.example.java_test_project.service.LogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stats")

public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/get-log")
    public List<LogEntry> getLog() throws IOException {
        String filePath = "logs-2023-08.log";
        return logService.readLogFile(filePath);
    }

    @GetMapping("/{year}/{month}/codes")
    public Map<Integer, Long> getHttpCodes(@PathVariable String year, @PathVariable String month) throws IOException {
        String fileName = String.format("logs-%s-%s.log", year, month);
        return logService.countHttpCodes(fileName);
    }

    @GetMapping("/{year}/{month}/avg-duration")
    public Map<String, Double> getAverageRequestDuration(@PathVariable String year, @PathVariable String month) throws IOException {
        String fileName = String.format("logs-%s-%s.log", year, month);
        return logService.averageRequestDuration(fileName);
    }
}
