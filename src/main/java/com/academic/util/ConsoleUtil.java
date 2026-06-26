package com.academic.util;

public class ConsoleUtil {

    private static final int WIDTH = 60;

    public static void title(String title) {
        line();

        int padding = (WIDTH - title.length()) / 2;

        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }

        System.out.println(title);

        line();
    }

    public static void line() {
        System.out.println("=".repeat(WIDTH));
    }

    public static void separator() {
        System.out.println("-".repeat(WIDTH));
    }

    public static void success(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    public static void info(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void error(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void warning(String message) {
        System.out.println("[WARNING] " + message);
    }

    public static void blank() {
        System.out.println();
    }
}