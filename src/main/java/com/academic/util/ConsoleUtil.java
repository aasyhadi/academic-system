package com.academic.util;

public final class ConsoleUtil {

    private ConsoleUtil() {
        // Utility class, tidak boleh dibuat object
    }

    public static void title(String title) {
        System.out.println();
        System.out.println("==================================================");
        System.out.println(title);
        System.out.println("==================================================");
    }

    public static void success(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    public static void error(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void info(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void line() {
        System.out.println("--------------------------------------------------");
    }

    public static void blank() {
        System.out.println();
    }
}