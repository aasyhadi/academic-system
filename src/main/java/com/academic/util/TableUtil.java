package com.academic.util;

public class TableUtil {

    public static void printStudentHeader() {
        System.out.println("+----+----------+----------------------+------------+----------+");
        System.out.println("| ID | NIM      | Nama                 | Jurusan    | Semester |");
        System.out.println("+----+----------+----------------------+------------+----------+");
    }

    public static void printStudentRow(Long id, String nim, String name, String major, Integer semester) {
        System.out.printf("| %-2d | %-8s | %-20s | %-10s | %-8d |%n",
                id, nim, name, major, semester);
    }

    public static void printStudentFooter() {
        System.out.println("+----+----------+----------------------+------------+----------+");
    }

    public static void printLecturerHeader() {
        System.out.println("+----+------------+----------------------+--------------+");
        System.out.println("| ID | NIDN       | Nama                 | Departemen   |");
        System.out.println("+----+------------+----------------------+--------------+");
    }

    public static void printLecturerRow(Long id, String nidn, String name, String department) {
        System.out.printf("| %-2d | %-10s | %-20s | %-12s |%n",
                id, nidn, name, department);
    }

    public static void printLecturerFooter() {
        System.out.println("+----+------------+----------------------+--------------+");
    }

    public static void printCourseHeader() {
        System.out.println("+----+----------+----------------------+-----+----------+");
        System.out.println("| ID | Kode MK  | Nama MK              | SKS | Semester |");
        System.out.println("+----+----------+----------------------+-----+----------+");
    }

    public static void printCourseRow(Long id, String code, String name, Integer credit, String semester) {
        System.out.printf("| %-2d | %-8s | %-20s | %-3d | %-8s |%n",
                id, code, name, credit, semester);
    }

    public static void printCourseFooter() {
        System.out.println("+----+----------+----------------------+-----+----------+");
    }

    public static void printGradeHeader() {
        System.out.println("+----+----------+----------+-------+-------+");
        System.out.println("| ID | NIM      | Kode MK  | Nilai | Huruf |");
        System.out.println("+----+----------+----------+-------+-------+");
    }

    public static void printGradeRow(Long id, String nim, String courseCode, Double score, String letter) {
        System.out.printf("| %-2d | %-8s | %-8s | %-5.1f | %-5s |%n",
                id, nim, courseCode, score, letter);
    }

    public static void printGradeFooter() {
        System.out.println("+----+----------+----------+-------+-------+");
    }

    public static void printEnrollmentHeader() {
        System.out.println("+----+----------+----------+");
        System.out.println("| ID | NIM      | Kode MK  |");
        System.out.println("+----+----------+----------+");
    }

    public static void printEnrollmentRow(Long id, String nim, String courseCode) {
        System.out.printf("| %-2d | %-8s | %-8s |%n",
                id, nim, courseCode);
    }

    public static void printEnrollmentFooter() {
        System.out.println("+----+----------+----------+");
    }
}