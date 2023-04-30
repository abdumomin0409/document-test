package com.company.instagram;

import java.util.UUID;

public class StringUtils {
    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static String generateFileName() {
        return UUID.randomUUID().toString();
    }
}
