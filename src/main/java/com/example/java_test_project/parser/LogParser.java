package com.example.java_test_project.parser;


import com.example.java_test_project.model.LogEntry;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LogParser {

    private static final Pattern logPattern = Pattern.compile(
            "(\\S+)\\s+" +          // ip
            "-\\s+-\\s+" +          // dashes
            "\\[(.*?)]\\s+" +       // timestamp
            "\"(\\S+)\\s+" +        // http method
            "(.*?)\"\\s+" +         // page
            "(\\d{3})\\s+" +        // http code
            "(\\d+)\\s+" +          // response size
            "(\\d+)"                // duration
    );

    public LogEntry parseLine(String line) {
        try {
            Matcher matcher = logPattern.matcher(line);

            if (matcher.find()) {
                String page = matcher.group(4);
                if (page.startsWith("/")) {
                    page = page.substring(1);
                }
                int httpCode = Integer.parseInt(matcher.group(5));
                int duration = Integer.parseInt(matcher.group(7));

                return new LogEntry(page, httpCode, duration);
            } else {
                System.err.println("log didn't match pattern: " + line);
                return null;
            }
        } catch (Exception e) {
            System.err.println("failed to parse line: " + line);
            return null;
        }
    }
}
