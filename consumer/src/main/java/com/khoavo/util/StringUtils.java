package com.khoavo.util;

import java.util.Objects;

public class StringUtils {
    public static boolean isNotBlank(String str) {
        if (Objects.isNull(str)) {
            return false;
        }

        return !str.isBlank();
    }
}
