package com.example.java_test_project.model;

public class LogEntry {
    private String page;
    private int httpCode;
    private int duration;

    public LogEntry(String page, int httpCode, int duration) {
        this.page = page;
        this.httpCode = httpCode;
        this.duration = duration;
    }

    public String getPage() {
        return page;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public int getDuration() {
        return duration;
    }
}
