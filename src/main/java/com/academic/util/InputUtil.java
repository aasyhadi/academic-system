package com.academic.util;

import java.util.Scanner;

public class InputUtil {

    private static final Scanner input = new Scanner(System.in);

    public static String inputString(String label) {
        System.out.print(label);
        return input.nextLine();
    }

    public static Integer inputInteger(String label) {
        System.out.print(label);
        Integer value = input.nextInt();
        input.nextLine();
        return value;
    }

    public static Long inputLong(String label) {
        System.out.print(label);
        Long value = input.nextLong();
        input.nextLine();
        return value;
    }

    public static Double inputDouble(String label) {
        System.out.print(label);
        Double value = input.nextDouble();
        input.nextLine();
        return value;
    }
}