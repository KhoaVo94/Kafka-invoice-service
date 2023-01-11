package com.khoavo.domain;

import com.khoavo.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class InvoiceNumber {
    String value;

    public static final String PREFIX = "INV-";

    public static InvoiceNumber randGenerate() {
        UUID uuid = UUID.randomUUID();
        return new InvoiceNumber(PREFIX + uuid.toString());
    }

    @Override
    public String toString() {
        if (StringUtils.isNotBlank(value)) {
            return value;
        }

        return "";
    }
}
