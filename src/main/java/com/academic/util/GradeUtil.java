package com.academic.util;

public class GradeUtil {

    public static String toLetter(Double score) {
        if (score >= 85) {
            return "A";
        }

        if (score >= 75) {
            return "B";
        }

        if (score >= 65) {
            return "C";
        }

        if (score >= 50) {
            return "D";
        }

        return "E";
    }
}