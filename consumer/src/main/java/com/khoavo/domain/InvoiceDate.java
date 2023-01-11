package com.khoavo.domain;

import com.khoavo.util.StringUtils;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
public class InvoiceDate {
    private String value;
    private LocalDateTime invoiceDate;

    private String DATETIME_FORMATTER = "dd/mm/yyyy HH:mm";

    public static InvoiceDate ofDefault() {
        return new InvoiceDate(LocalDateTime.now());
    }

    public InvoiceDate(String date) {
        invoiceDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DATETIME_FORMATTER));
        value = toString();
    }

    public InvoiceDate(LocalDateTime dateTime) {
        this.invoiceDate = dateTime;
        value = toString();
    }

    @Override
    public String toString() {
        if (StringUtils.isNotBlank(value)) {
            return value;
        }

        if (Objects.isNull(invoiceDate)) {
            return "";
        }

        return invoiceDate.format(DateTimeFormatter.ofPattern(DATETIME_FORMATTER));
    }
}
