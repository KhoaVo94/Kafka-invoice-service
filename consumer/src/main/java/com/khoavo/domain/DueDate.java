package com.khoavo.domain;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DueDate {
    private LocalDateTime dateTime;

    private String DATETIME_FORMATTER = "dd/mm/yyyy HH:mm";

    @Value("${spring.default.due-days}")
    static Integer DEFAULT_DUE_DAY;

    public static DueDate ofDefault() {
        return new DueDate(LocalDateTime.now().plusDays(DEFAULT_DUE_DAY));
    }

    public DueDate(String date) {
        dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DATETIME_FORMATTER));
    }

    public DueDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String dueDateStr() {
        if (Objects.isNull(dateTime)) {
            return "";
        }

        return dateTime.format(DateTimeFormatter.ofPattern(DATETIME_FORMATTER));
    }
}
