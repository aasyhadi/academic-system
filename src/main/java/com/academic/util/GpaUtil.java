package com.academic.util;

public class GpaUtil {

    public static double scoreToPoint(Double score) {
        if (score >= 85) {
            return 4.0;
        }

        if (score >= 75) {
            return 3.0;
        }

        if (score >= 65) {
            return 2.0;
        }

        if (score >= 50) {
            return 1.0;
        }

        return 0.0;
    }
}