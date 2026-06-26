package com.academic.util;

import java.util.List;

public class GenericTableUtil {

    public static void print(String[] headers, List<String[]> rows) {
        int[] widths = calculateWidths(headers, rows);

        printLine(widths);
        printRow(headers, widths);
        printLine(widths);

        for (String[] row : rows) {
            printRow(row, widths);
        }

        printLine(widths);
        System.out.println("Total Data: " + rows.size());
    }

    private static int[] calculateWidths(String[] headers, List<String[]> rows) {
        int[] widths = new int[headers.length];

        for (int i = 0; i < headers.length; i++) {
            widths[i] = headers[i].length();
        }

        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                if (row[i].length() > widths[i]) {
                    widths[i] = row[i].length();
                }
            }
        }

        return widths;
    }

    private static void printLine(int[] widths) {
        System.out.print("+");

        for (int width : widths) {
            System.out.print("-".repeat(width + 2));
            System.out.print("+");
        }

        System.out.println();
    }

    private static void printRow(String[] columns, int[] widths) {
        System.out.print("|");

        for (int i = 0; i < columns.length; i++) {
            System.out.printf(" %-" + widths[i] + "s |", columns[i]);
        }

        System.out.println();
    }
}