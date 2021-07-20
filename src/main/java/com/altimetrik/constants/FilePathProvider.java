package com.altimetrik.constants;

public class FilePathProvider {
    private static final String filepath = FilePathProvider.class.getClassLoader().getResource(".").getFile() + "payments.csv";

    public static String getPath() {
        return filepath;
    }
}
